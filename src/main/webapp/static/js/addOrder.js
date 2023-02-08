
let currentOrder = [];
let billedOrder = [];
let currentTransaction = {};
let currentOrderId = -1;
let total = 0;
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
function getInventoryUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}
//BUTTON ACTIONS
function addOrder(){
    if(currentTransaction.productId === undefined){
        toastr.error("invalid barcode");
        return;
    }
    var url = getInventoryUrl() + "/" + currentTransaction.productId;
    $.ajax({
       url: url,
       type: 'GET',
       async:false,
       success: function(data) {
            let currentQuantity = currentTransaction.quantity? currentTransaction.quantity:0;
            let price = $('#inputPrice').val();
            if(currentOrder.length == 0){
                if(currentQuantity < data.quantity){
                    currentOrder.push(currentTransaction);
                    total+=price*currentQuantity;
                    $('#total').text(total);
                    currentTransaction = {};
                    resetOrder();
                    displayOrderList(currentOrder);
                    return;
                }
            }
            for(var transaction of currentOrder){
                if(transaction.productId === data.id){
                    if(transaction.quantity+currentQuantity <= data.quantity){
                        toastr.error("given quantity is larger than the inventory");
                        return
                    }else{
                        if(currentQuantity > 0){
                        //duplicate entry
                        transaction.quantity += currentQuantity;
                        transaction.sellingPrice += currentQuantity*price;
                        total += currentQuantity*price;
                        currentTransaction = {};
                        $('#total').text(total);
                        displayOrderList(currentOrder);
                        resetOrder();
                        return
                        }
                    }
                }
            };
            //new entry of transaction
            if(currentQuantity <= data.quantity){
                currentOrder.push(currentTransaction);
                total += currentQuantity*price;
            }else{
                toastr.error("given quantity is larger than the inventory");
                return;
            }
            currentTransaction = {};
            $('#total').text(total);
            displayOrderList(currentOrder);
            resetOrder();
       },
       error: handleAjaxError
    });
}
function deleteTransaction(barcode){
    currentOrder = currentOrder.filter(transaction=>{
        if(transaction.barcode === barcode){
            total-=transaction.sellingPrice;
            $('#total').text(total);
            return false;
        }
        return true;
    });
    displayOrderList(currentOrder);
}
function submitOrder(){
    if(currentOrder.length == 0){
        toastr.error("please add items");
        return
    }
    var url = getOrderUrl();
    const now = Date.now();
    var data = {"date": now}
    var dataJson = JSON.stringify(data);
    //getting order id for order
    $.ajax({
       url: url,
       type: 'POST',
       data: dataJson,
       headers: {
         'Content-Type': 'application/json'
       },
       success: function(data) {
            currentOrderId = data;
            makeOrder();
       },
       error: handleAjaxError
    });
}
function makeOrder(){
    var url = getOrderUrl()+"/order";
    var orderItemList = currentOrder.map(transaction=>{
        var orderItemObject = {"productId":transaction.productId,"orderId":currentOrderId,"quantity":transaction.quantity,"sellingPrice":transaction.sellingPrice};
        return orderItemObject;
    })
    var orderItemJson = JSON.stringify(orderItemList);
    $.ajax({
       url: url,
       type: 'POST',
       data: orderItemJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(response) {
            toastr.success("Order placed, you can download the invoice");
            $('#get-invoice').show();
            $('#add-order').prop('disabled', true);
            $('#submit-order').prop('disabled', true);
       },
       error: handleAjaxError
    });
}
function resetOrder(){
    $('#inputBarcode').val('');
    $('#inputPrice').val(0);
    $('#inputQuantity').val(1);
    $('#inputMrp').val(0);
    $('#inputName').val("");
    $('#inputSellingPrice').val(0);
    currentTransaction = {};
}
function updatePrice(){
    let quantity = $("#inputQuantity").val();
    let barcode = $("#inputBarcode").val();
    var url = getOrderUrl() + "?" + "barcode=" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(data) {
           let price = $('#inputPrice').val();
           let sellingPrice = price*quantity;
           $('#inputMrp').val(data.mrp);
           $('#inputName').val(data.name);
           $('#inputSellingPrice').val(sellingPrice);
           currentTransaction.sellingPrice = sellingPrice;
           currentTransaction.quantity = +quantity;
           currentTransaction.productId = data.productId;
           currentTransaction.barcode = data.barcode;
           currentTransaction.name = data.name;
           currentTransaction.mrp = data.mrp;
       },
       error: handleAjaxError
    });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


//UI DISPLAY METHODS
function displayOrderList(currentOrder){
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    for(var product of currentOrder){
        var buttonHtml = `<button class="btn btn-primary btn-sm" onclick="deleteTransaction('${product.barcode}')">delete</button>`
        var row = '<tr>'
        + '<td>' + product.name + '</td>'
        + '<td>'  + product.quantity + '</td>'
        + '<td>' + product.sellingPrice + '</td>'
        + '<td>' + buttonHtml + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
}
function getInvoice(){
    var url = getOrderUrl()+"/pdf/"+currentOrderId;
    $.ajax({
       url: url,
       type: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
       success: function(blob) {
            var link=document.createElement('a');
            link.href=window.URL.createObjectURL(blob);
            link.download="Invoice_" + new Date() + ".pdf";
            link.click();
            toastr.info("Invoice is Downloading");
       },
       error: handleAjaxError
    });
}
//VALIDATION FUNCTIONS
function limitDecimalPlaces(event) {
  if (event.target.value.indexOf('.') == -1) { return; }
  if ((event.target.value.length - event.target.value.indexOf('.')) > 2) {
    event.target.value = parseFloat(event.target.value).toFixed(2);
  }
}

//INITIALIZATION CODE
function keyBinding(){
    $("#inputBarcode").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-order").click();
        }
    });
    $("#inputQuantity").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-order").click();
        }
    });
    $("#inputPrice").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-order").click();
        }
    });
}
function init(){
	$('#add-order').click(addOrder);
	$('#get-invoice').click(getInvoice);
	$('#get-invoice').hide();
	$('#submit-order').click(submitOrder);
    $("#inputQuantity").on('input',updatePrice);
    $("#inputPrice").on('change',updatePrice);
    $("#inputPrice").on('change',limitDecimalPlaces);
    $("#inputBarcode").on('change',updatePrice);
    keyBinding();
}
$(document).ready(init);

