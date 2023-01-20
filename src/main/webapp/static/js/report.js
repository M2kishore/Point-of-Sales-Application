var orders = [];
var products = {};
var brandCategory = {};
var inventory = {};
var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
function getReportUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}
function getProductUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
function getBrandUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
function getInventoryUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}
function getReport(){
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    var startDateTimestamp = Date.parse(startDate);
    var endDateTimestamp = Date.parse(endDate);
    var startDateMilliSeconds = new Date(startDateTimestamp).getTime();
    var endDateMilliSeconds = new Date(endDateTimestamp).getTime();
    var DateObject = {"endDate":endDateMilliSeconds,"startDate":startDateMilliSeconds};
    var DateJson = JSON.stringify(DateObject);
    var url = getReportUrl();

    $.ajax({
       url: url,
       type: 'POST',
       data: DateJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success:async function(response) {
            $("#order-count").text(response.length);
            await getQuantityAndSellingPrice(response);
       },
       error: handleAjaxError
    });
}
function getQuantityAndSellingPrice(orderArray){
    var url = getReportUrl()+"/order";
    var startId = orderArray.shift();
    var endId = orderArray.pop();
    var idObject = {"endId":endId,"startId":startId};
    var idJson = JSON.stringify(idObject);
    $.ajax({
       url: url,
       type: 'POST',
       data: idJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(orderItems) {
       orders = [...orderItems];
            var quantity = 0;
            var revenue = 0;
            orderItems.map(item=>{
                getProductData(item.productId);
                quantity += item.quantity;
                revenue += item.sellingPrice;
            });
            $("#item-count").text(quantity);
            $("#total-revenue").text(revenue);
       },
       error: handleAjaxError
    });
}
function getProductData(productId){
if(products.hasOwnProperty(productId)){
    return;
}
   var url = getProductUrl() + "/" + productId;
   	$.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(productData) {
   	        products[productData.id] = productData;
   	        getBrandCategory(productData.brandCategory);
   	   },
   	   error: handleAjaxError
   	});
}
function getBrandCategory(id){
    if(brandCategory.hasOwnProperty(id)){
        return;
    }
    var url = getBrandUrl() + "/" + id;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(brandCategoryData) {
            brandCategory[id] = brandCategoryData;
            getInventory(id);
       },
       error: handleAjaxError
    });
}
function getInventory(id){
    if(inventory.hasOwnProperty(id)){
      return;
    }
    var url = getInventoryUrl() + "/" + id;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(inventoryData) {
            inventory[id] = inventoryData;
       },
       error: handleAjaxError
    });
}
function getInventoryReport(){
    $('#category-table-div').hide();
    $('#brand-table-div').hide();
    $('#inventory-table-div').show();
    let inventoryReport = {};
    orders.map(order=>{
        var id = brandCategory[products[order.productId]["brandCategory"]]["id"];
        var brand = brandCategory[products[order.productId]["brandCategory"]]["brand"];
        var category = brandCategory[products[order.productId]["brandCategory"]]["category"];
        if(!inventoryReport.hasOwnProperty(id)){
            inventoryReport[id] = {"brand":brand,"category":category,"quantity":0};
        }
        inventoryReport[id]["quantity"] += order.quantity;
    });
    var $tbody = $('#inventory-table').find('tbody');
    $tbody.empty();
    for(var id in inventoryReport){
        var reportObject = inventoryReport[id];
        var row = '<tr>'
        + '<td>' + reportObject.brand + '</td>'
        + '<td>' + reportObject.category + '</td>'
        + '<td>'  + reportObject.quantity + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
}
function getBrandReport(){
    $('#category-table-div').hide();
    $('#brand-table-div').show();
    $('#inventory-table-div').hide();
    let brandReport = {};
    orders.map(order=>{
        var brand = brandCategory[products[order.productId]["brandCategory"]]["brand"];
        if(!brandReport.hasOwnProperty(brand)){
            brandReport[brand] = {"quantity":0,"revenue":0};
        }
        brandReport[brand]["quantity"] += order.quantity;
        brandReport[brand]["revenue"] += order.sellingPrice;
    });
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    for(var brand in brandReport){
        var reportObject = brandReport[brand];
        var row = '<tr>'
        + '<td>' + brand + '</td>'
        + '<td>' + reportObject.quantity + '</td>'
        + '<td>'  + reportObject.revenue + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
}
function getCategoryReport(){
    $('#category-table-div').show();
    $('#brand-table-div').hide();
    $('#inventory-table-div').hide();
    let categoryReport = {};
    orders.map(order=>{
        var category = brandCategory[products[order.productId]["brandCategory"]]["category"];
        if(!categoryReport.hasOwnProperty(category)){
            categoryReport[category] = {"quantity":0,"revenue":0};
        }
        categoryReport[category]["quantity"] += order.quantity;
        categoryReport[category]["revenue"] += order.sellingPrice;
    });
    var $tbody = $('#category-table').find('tbody');
    $tbody.empty();
    for(var category in categoryReport){
        var reportObject = categoryReport[category];
        var row = '<tr>'
        + '<td>' + category + '</td>'
        + '<td>' + reportObject.quantity + '</td>'
        + '<td>'  + reportObject.revenue + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
}
//INITIALIZATION CODE

function init(){
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    var nextDate = new Date();
    nextDate.setDate(today.getDate()+1);
    $('#startDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        maxDate: today
    });
    $('#endDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        minDate: function () {
            return $('#startDate').val();
        },
        maxDate: nextDate
    });
$('#getReport').click(getReport);
$('#getBrandReport').click(getBrandReport);
$('#getCategoryReport').click(getCategoryReport);
$('#getInventoryReport').click(getInventoryReport);
$('#endDate').on('change',getReport);
$('#startDate').on('change',getReport)
$('#category-table-div').hide();
$('#brand-table-div').hide();
$('#inventory-table-div').hide();
}
$(document).ready(init);