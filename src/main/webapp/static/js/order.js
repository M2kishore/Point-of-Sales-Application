
let currentOrder = [];
let currentTransaction = {};
let currentOrderId = -1;
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
//BUTTON ACTIONS
function addOrder(){
currentOrder.push(currentTransaction);
currentTransaction.orderId = currentOrderId;
currentTransaction = {};
displayOrderList(currentOrder);
//console.log(currentOrder);
//var json = JSON.stringify(currentOrder);
//var url = getOrderUrl()+"/order";
//$.ajax({
//       url: url,
//       type: 'POST',
//       data: json,
//       headers: {
//        'Content-Type': 'application/json'
//       },
//       success: function(response) {
//           console.log(response);
//           getOrderList();
//       },
//       error: handleAjaxError
//    });

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

function submitOrder(){
    currentOrder.map(transaction=>{
        var orderItemObject = {"productId":transaction.productId,"orderId":transaction.orderId,"quantity":transaction.quantity,"sellingPrice":transaction.sellingPrice};
        console.table(orderItemObject);
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
               console.log(response);
           },
           error: handleAjaxError
        });
    });
}


function getOrderList(){

}
//var url = getOrderUrl() + "/" + "all";
//
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayOrderList(data);
//	   },
//	   error: handleAjaxError
//	});
//}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});
}

function updatePrice(){
    let quantity = $("#inputQuantity").val();
    let barcode = $("#inputBarcode").val();
    var url = getOrderUrl() + "?" + "barcode=" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(data) {
       console.log(data);
       let price = data.sellingPrice*quantity;
       $('#inputPrice').val(price);
       currentTransaction.sellingPrice = price;
       currentTransaction.quantity = quantity;
       currentTransaction.productId = data.productId;
       currentTransaction.barcode = data.barcode;
       currentTransaction.name = data.name;

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

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getOrderUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS
function deleteTransaction(barcode){
    currentOrder = currentOrder.filter(transaction=>transaction.barcode != barcode);
    displayOrderList(currentOrder);
}
function displayOrderList(currentOrder){
console.log(currentOrder)
var $tbody = $('#order-table').find('tbody');
$tbody.empty();
for(var product of currentOrder){
    var buttonHtml = ' <button onclick="deleteTransaction(' + product.barcode + ')">delete</button>'
    var row = '<tr>'
    + '<td>' + product.name + '</td>'
    + '<td>'  + product.quantity + '</td>'
    + '<td>' + product.sellingPrice + '</td>'
    + '<td>' + buttonHtml + '</td>'
    + '</tr>';
    $tbody.append(row);
}

//	var $tbody = $('#order-table').find('tbody');
//	$tbody.empty();
//	for(var i in data){
//		var e = data[i];
//		var buttonHtml = ' <button onclick="displayEditOrder(' + e.id + ')">edit</button>'
//		var row = '<tr>'
//		+ '<td>' + e.orderId + '</td>'
//		+ '<td>' + e.productId + '</td>'
//		+ '<td>'  + e.quantity + '</td>'
//		+ '<td>' + e.sellingPrice + '</td>'
//		+ '<td>' + buttonHtml + '</td>'
//		+ '</tr>';
//        $tbody.append(row);
//	}
}

function displayEditOrder(id){
	var url = getOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrder(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#orderFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#orderFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-order-modal').modal('toggle');
}

function displayOrder(data){
	$("#order-edit-form input[name=barcode]").val(data.barcode);
	$("#order-edit-form input[name=quantity]").val(data.quantity);
	$("#order-edit-form input[name=name]").val(data.name);
	$("#order-edit-form input[name=price]").val(data.sellingPrice);
	$("#order-edit-form input[name=id]").val(data.id);
	$('#edit-order-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#submit-order').click(submitOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName)
    $("#inputQuantity").on('input',updatePrice);
    $("#inputBarcode").on('change',updatePrice);
}

function getOrderId(){
    var url = getOrderUrl();
    const now = Date.now();
    var data = {"date": now}
    var dataJson = JSON.stringify(data);
    $.ajax({
       url: url,
       type: 'POST',
       data: dataJson,
       headers: {
         'Content-Type': 'application/json'
       },
       success: function(data) {
            currentOrderId = data;
            $('#orderNumber').html("#"+data);
       },
       error: handleAjaxError
    });
}

$(document).ready(init);
$(document).ready(getOrderId);
$(document).ready(getOrderList);

