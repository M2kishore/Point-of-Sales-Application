let brandList = [];
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
	        toastr.success("Product Added Successfully");
	   		getProductList();
	   },
	   error: handleAjaxError
	});
	return false;
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
	        toastr.success("Product Updated Successfully");
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
	   		displayProductList(data);
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
        toastr.error("File Rows should be within 5000 rows");
        return;
    }
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
	    if(errorData.length > 0){
            toastr.warning("Errors while processing the file");
            toastr.warning("Download the Error File by Clicking the 'Download Errors' button");
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

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml =  '<button class="btn btn-primary btn-sm" onclick="displayEditProduct(' + e.id + ')">edit</button>';
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
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
    keyBinding();
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(setupBrandCategorySelect);