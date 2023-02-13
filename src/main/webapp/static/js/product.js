let brandList = [];
let productData = [];
function getProductUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
function getBrandUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var url = getProductUrl();
	var barcode = $('#inputBarcode').val();
	var name = $('#inputName').val();
	var mrp = $('#inputMrp').val();
	var brandCategory = $('#brand-category-select').children("option:selected").val();
	checkValidity(barcode,name,mrp,brandCategory);
	var newProduct = {"barcode":barcode,"name":name,"mrp":mrp,"brandCategory":brandCategory};
	var newProductJson = JSON.stringify(newProduct);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: newProductJson,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        toastr.success("Product Added Successfully","Success",{"timeOut": 5000,});
	   		getProductList();
	   },
	   error: handleAjaxError
	});
	return false;
}
function checkValidity(barcode,name,mrp,brandCategory){
    if(brandCategory === 0){
        toastr.warning("Please Select a Valid Brand Category Combination","Warning");
        return false;
    }
    if(barcode == ""){
        toastr.warning("Barcode is Empty","Warning");
        return false;
    }
    if(name == ""){
        toastr.warning("Name is Empty","Warning");
        return false;
    }
    if(mrp == ""){
        toastr.warning("Mrp is Empty","Warning");
        return false;
    }
    return true;
}
function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        toastr.success("Product Updated Successfully","Success",{"timeOut": 5000,});
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	        productData = [...data];
	   		displayProductList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
    //no of rows check
    if (fileData.length>5000){
        toastr.error("File Rows should be within 5000 rows","Error");
        return;
    }
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
	    if(errorData.length > 0){
            toastr.warning("Errors while processing the file","Warning");
            toastr.warning("Download the Error File by Clicking the 'Download Errors' button","Warning");
        }
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getProductUrl();

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
function displayBrandCategorySelect(brandCategoryList){
    var brandCategorySelect = $('#brand-category-select');
    brandCategorySelect.empty();
    brandCategorySelect.append("<option value=0>Choose ...</option>");
    for(brand of brandCategoryList){
        brandCategorySelect.append("<option value="+brand.id+">"+brand.brand+" "+brand.category+"</option>")
    }
}

function setupBrandCategorySelect(){
    var url = getBrandUrl();
    $.ajax({
       url: url,
       type: 'GET',
       success: function(brandData) {
        brandList = [...brandData];
        displayBrandCategorySelect(brandData);
       },
       error: handleAjaxError
    });

}

function displayProductList(){
    var searchString = $('#inputSearch').val();
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in productData){
		var e = productData[i];
		if(!e.barcode.includes(searchString) && !e.name.includes(searchString)){
            continue;
        }
		var buttonHtml =  '<button class="btn btn-primary btn-sm" onclick="displayEditProduct(' + e.id + ')">Edit</button>';
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

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=brandCategory]").val(data.brandCategory);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$("#product-edit-form input[name=id]").val(data.id);
	$('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE
function keyBinding(){
    $("#inputBarcode").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-product").click();
        }
    });
    $("#brand-category-select").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-product").click();
        }
    });
    $("#inputName").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-product").click();
        }
    });
    $("#inputMrp").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-product").click();
        }
    });
}
function limitDecimalPlaces(event) {
  if (event.target.value.indexOf('.') == -1) { return; }
  if ((event.target.value.length - event.target.value.indexOf('.')) > 2) {
    event.target.value = parseFloat(event.target.value).toFixed(2);
  }
}
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
    $("#inputMrp").on('change',limitDecimalPlaces);
    $("#inputSearch").on('input',displayProductList);
    keyBinding();
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(setupBrandCategorySelect);