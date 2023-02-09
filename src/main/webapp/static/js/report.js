var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
function getReportUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}
function getInventoryReport(){
    $('#sales-table-div').hide();
    $('#brand-table-div').hide();
    $('#inventory-table-div').show();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/inventory?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#inventory-table').find('tbody');
    $tbody.empty();
    $.ajax({
       url: url,
       type: 'POST',
       headers: {
        'Content-Type': 'application/json'
       },
       success:async function(inventoryReportData) {
            for(var row of inventoryReportData){
                 var row = '<tr>'
                    + '<td>' + row.brand + '</td>'
                    + '<td>' + row.category + '</td>'
                    + '<td>'  + row.quantity + '</td>'
                    + '</tr>';
                 $tbody.append(row);
            }
       },
       error: handleAjaxError
    });
}
function getBrandReport(){
    $('#sales-table-div').hide();
    $('#brand-table-div').show();
    $('#inventory-table-div').hide();
    var dateJson = getDateJson();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/brand?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
     $.ajax({
       url: url,
       type: 'POST',
       data: dateJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success:async function(brandReportData) {
            for(var row of brandReportData){
                 var row = '<tr>'
                    + '<td>' + row.brand + '</td>'
                    + '<td>' + row.quantity + '</td>'
                    + '<td>'  + row.revenue + '</td>'
                    + '</tr>';
                 $tbody.append(row);
            }
       },
       error: handleAjaxError
    });
}
function getSalesReport(){
    $('#sales-table-div').show();
    $('#brand-table-div').hide();
    $('#inventory-table-div').hide();
    var dateJson = getDateJson();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/sales?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#sales-table').find('tbody');
    $tbody.empty();
        $.ajax({
           url: url,
           type: 'POST',
           data: dateJson,
           headers: {
            'Content-Type': 'application/json'
           },
           success:async function(salesReportData) {
                for(var row of salesReportData){
                     var row = '<tr>'
                        + '<td>' + row.brand + '</td>'
                        + '<td>' + row.category + '</td>'
                        + '<td>' + row.quantity + '</td>'
                        + '<td>'  + row.revenue + '</td>'
                        + '</tr>';
                     $tbody.append(row);
                }
           },
           error: handleAjaxError
        });
}
function getDateJson(){
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    brandString = $('#inputBrand').val();
    categoryString = $('#inputCategory').val();
    var startDateTimestamp = Date.parse(startDate);
    var endDateTimestamp = Date.parse(endDate);
    var startDateMilliSeconds = new Date(startDateTimestamp).getTime();
    var endDateMilliSeconds = new Date(endDateTimestamp).getTime();
    var DateObject = {"endDate":endDateMilliSeconds,"startDate":startDateMilliSeconds};
    var DateJson = JSON.stringify(DateObject);
    return DateJson;
}
//INITIALIZATION CODE

function init(){
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    var nextDate = new Date();
    nextDate.setDate(today.getDate()+1);
    var minimumStartDate = new Date();
    minimumStartDate.setDate(today.getDate()-365);
    var initialStartDate = new Date();
    var initialEndDate = new Date();
    initialStartDate.setDate(today.getDate() - 90);
    $('#startDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        maxDate: today,
        minDate: minimumStartDate
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
//$('#getReport').click(getReport);
$('#getBrandReport').click(getBrandReport);
$('#getSalesReport').click(getSalesReport);
$('#getInventoryReport').click(getInventoryReport);
//$('#endDate').on('change',getReport);
$('#endDate').val((initialEndDate.getMonth()+1)+"/"+initialEndDate.getDate()+"/"+initialEndDate.getFullYear());
//$('#startDate').on('change',getReport)
$('#startDate').val(initialStartDate.getMonth()+1+"/"+initialStartDate.getDate()+"/"+initialStartDate.getFullYear());
//$('#inputBrand').on('change',getReport)
//$('#inputCategory').on('change',getReport)
$('#sales-table-div').hide();
$('#brand-table-div').hide();
$('#inventory-table-div').hide();
}
$(document).ready(init);