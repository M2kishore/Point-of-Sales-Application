
let currentOrder = {};
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getProductUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
//BUTTON ACTIONS
function addOrder(event){
console.log(currentOrder);
	//Set the values to update
//	var $form = $("#order-form");
//	var json = toJson($form);
//	var url = getOrderUrl();
//	$.ajax({
//	   url: url,
//	   type: 'POST',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		getOrderList();
//	   },
//	   error: handleAjaxError
//	});
//
//	return false;
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


function getOrderList(){
	var url = getOrderUrl();
	const now = Date.now();
	var data = {"date": now}
	var dataJson = JSON.stringify(data);
	console.log(dataJson);
	$.ajax({
       url: url,
       type: 'POST',
       data: dataJson,
       headers: {
         'Content-Type': 'application/json'
       },
       success: function(data) {
            currentOrder.id = data;
            console.log(currentOrder);
       },
       error: handleAjaxError
    });
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayOrderList(data);
//	   },
//	   error: handleAjaxError
//	});
}

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
    console.log(url);
    console.log(quantity,barcode,"lol");
    $.ajax({
       url: url,
       type: 'GET',
       success: function(data) {
       let price = data.price*quantity;
       console.log(data,quantity);
       console.log(price);
       $('#inputPrice').val(price);
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

function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteOrder(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditOrder(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brandCategory + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
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
	$("#order-edit-form input[name=price]").val(data.price);
	$("#order-edit-form input[name=id]").val(data.id);
	$('#edit-order-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName)
    $("#inputQuantity").on('input',updatePrice);
    $("#inputBarcode").on('change',updatePrice);
}

$(document).ready(init);
$(document).ready(getOrderList);

