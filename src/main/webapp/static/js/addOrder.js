
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
    alert("invalid barcode");
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
    	                console.log(currentOrder);
    	                currentTransaction = {};
    	                resetOrder();
    	                displayOrderList(currentOrder);
    	                return;
    	            }
    	        }
    	        for(var transaction of currentOrder){
    	            if(transaction.productId === data.id){
    	                if(transaction.quantity+currentQuantity <= data.quantity){
    	                    alert("given quantity is larger than the inventory");
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
                    alert("given quantity is larger than the inventory");
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

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-edit-form input[name=id]").val();
	var url = getOrderUrl() + "/" + id;

	//Set the values to update
	var $form = $("#order-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}
function updateInventory(id,quantity,transaction){
    var url = getInventoryUrl() + "/" + id;
    var newInventory = {"id":id,"quantity":quantity};
    var newInventoryJson = JSON.stringify(newInventory);
    $.ajax({
       url: url,
       type: 'PUT',
       data: newInventoryJson,
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(response) {
            var orderItemObject = {"productId":transaction.productId,"orderId":currentOrderId,"quantity":transaction.quantity,"sellingPrice":transaction.sellingPrice};
            var orderItemObjectJson = JSON.stringify(orderItemObject);
            var url = getOrderUrl()+"/order";
            $.ajax({
               url: url,
               type: 'POST',
               data: orderItemObjectJson,
               headers: {
                'Content-Type': 'application/json'
               },
               success: function(response) {
                    billedOrder.push(transaction);
               },
               error: handleAjaxError
            });
       },
       error: handleAjaxError
    });
}
function getProductInformation(){
    currentOrder.map(transaction=>{
        var url = getInventoryUrl() + "/" +transaction.productId;
        $.ajax({
           url: url,
           type: 'GET',
           success: function(data) {
                let newQuantity = data.quantity-transaction.quantity;
                if(newQuantity >= 0){
                    updateInventory(data.id,newQuantity,transaction);
                }else{
                    alert("insufficient inventory for "+transaction.name);
                }
           },
           error: handleAjaxError
        });
    });
    //disable add and enable invoice
    alert("success, refresh for new order or click invoice to download invoice");
    $('#add-order').prop('disabled', true);
    $('#get-invoice').prop('disabled', false);
    $('#submit-order').prop('disabled', true);
}
function submitOrder(){
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
            getProductInformation();
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
           if(data.mrp < price){
                alert("Price cannot be greater than mrp");
                return;
           }
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


function processData(){
	var file = $('#orderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}


//UI DISPLAY METHODS
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
function displayOrderList(currentOrder){
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    for(var product of currentOrder){
        var buttonHtml = `<button onclick="deleteTransaction('${product.barcode}')">delete</button>`
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
    var url = getOrderUrl()+"/pdf";
    var total = billedOrder.reduce((total,transaction)=>total+transaction.sellingPrice,0);
    var orderDate = new Date().toString();
    var orderObject = {"billForm":billedOrder,"total":total,"orderId":currentOrderId,"date":orderDate};
    var orderJson = JSON.stringify(orderObject);
    console.log(orderJson)
        $.ajax({
           url: url,
           type: 'POST',
             data: orderJson,
             headers: {
            'Content-Type': 'application/json'
           },
             xhrFields: {
            responseType: 'blob'
         },
           success: function(blob) {
                console.log(blob.size);
                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download="Invoice_" + new Date() + ".pdf";
                link.click();
           },
           error: handleAjaxError
        });
}

//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#get-invoice').click(getInvoice);
	$('#submit-order').click(submitOrder);
	$('#process-data').click(processData);
    $("#inputQuantity").on('input',updatePrice);
    $("#inputPrice").on('change',updatePrice);
    $("#inputBarcode").on('change',updatePrice);
}
$(document).ready(init);

