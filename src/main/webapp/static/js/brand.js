var brandData = [];
function getBrandUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addBrand(event){
	//Set the values to update
	var $form = $("#brand-form");
	var brandString = $("#inputBrand").val();
	var categoryString = $("#inputCategory").val();
	if(brandString.trim() === ""){
	    toastr.warning("Brand Field is Empty","Warning");
	    return;
	}
	if(categoryString.trim() === ""){
        toastr.warning("Category Field is Empty","Warning");
        return;
    }
	var json = toJson($form);
	var url = getBrandUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        toastr.success("Brand Added Successfully","Success",{"timeOut": 5000,});
	   		getBrandList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateBrand(event){
	$('#edit-brand-modal').modal('toggle');
	//Get the ID
	var id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        toastr.success("Brand Updated Successfully","Success",{"timeOut": 5000,});
	   		getBrandList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	        brandData = [...data]
	   		displayBrandList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandFile')[0].files[0];
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
	var url = getBrandUrl();

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

function displayBrandList(){
    var searchString = $("#inputSearch").val();
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in brandData){
		var e = brandData[i];
		if(!e.brand.includes(searchString) && !e.category.includes(searchString)){
		    continue;
		}
		var buttonHtml = ' <button class="btn btn-primary btn-sm" onclick="displayEditBrand(' + e.id + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditBrand(id){
	var url = getBrandUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrand(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
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
	var $file = $('#brandFile');
	var fileName = $file.val();
	var allowedExtensions = /(\.tsv|\.csv)$/i;
	if (!allowedExtensions.exec(fileName)) {
        toastr.error("Invalid File Type","Error");
        $file.val('');
        $('#brandFileName').html("");
        return;
    }
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$("#brand-edit-form input[name=id]").val(data.id);
	$('#edit-brand-modal').modal('toggle');
}


//INITIALIZATION CODE
function keyBinding(){
    $("#inputBrand").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-brand").click();
        }
    });
    $("#inputCategory").keypress(function(event) {
        if (event.keyCode === 13) {
            $("#add-brand").click();
        }
    });
}
function init(){
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
    $("#inputSearch").on('input',displayBrandList);
    keyBinding();
}

$(document).ready(init);
$(document).ready(getBrandList);

