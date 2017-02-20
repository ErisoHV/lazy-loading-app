/**
 * Lazy Loading script
 * Erika
 */

/**
 * Upload the file to the server, and waits for the server response
 */
function uploadFileToProcess(){
	
	 // Form
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    $("#results").html("");
    $("#submitFile").prop("disabled", true);
		$.ajax({
			url: "http://localhost:8080/lazyloading/api/uploadFile",
			type: "POST",
			enctype: 'multipart/form-data',
			processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
		    data: data			
		})
		.done(function(response){
			form.reset();
			$("#submitFile").prop("disabled", false);
			showSuccessAlert(getFilesUrl(response));
		})
		.fail(function(data){
			$("#submitFile").prop("disabled", false);
			showErrorAlert("<strong>Error </strong> al ejecutar los c&aacute;lculos para Wilson, " +
					"verifique la conexi&oacute;n " +
			"con el servidor y el archivo de prueba, e intente nuevamente");
		});
	
}
/**
 * Builds the URL to access to file
 * @param response
 * @returns {String}
 */
function getFilesUrl(response){
	return response.host + "/lazyloading/api/files/" + response.fileName;
}

/**
 * Show a success alert
 * @param content
 */
function showSuccessAlert(content){
	$("#results").html("<div class='alert alert-success alert-dismissable fade in'>" +
			"<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
			"<strong>C&aacute;lculo exitoso! </strong>" +
			"<a href='" + content +"' >Descargar resultados </a></div>");
}

/**
 * Show an error alert
 * @param content
 */
function showErrorAlert(content){
	$("#results").html("<div class='alert alert-danger alert-dismissable fade in'>" +
			"<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" + content +
			"</div>");
}

/**
 * Open and dialog showing all the test cases
 */
function openTestCases(){
	$.get("http://localhost:8080/lazyloading/api/allCases")
	.done(function(data){

		$("#testCasesResults").empty();

		var table = $("<table id='testCasesTable' class='table table-striped'>" +
				"<thead><th>Identificaci&oacute;n</th><th>Fecha</th><th>Resultados</th></thead></table>")
		
		table.append("<tbody></tbody>");
		for (result of data){
			date = new Date(result.date);
			
			table.children('tbody').append("<tr>" +
					"<td>" + result.userId + "</td>" +
					"<td>" + date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear() + "</td>" +
					"<td><a href='" + getFilesUrl(result) + "'>Ver resultados</a></td>" +
					+ "</tr>")
		}
		
		$("#testCasesResults").append(table);
		$('#myModal').modal('show');
	})
	.fail(function(data){
		showErrorAlert("<strong>Error </strong> al recuperar los resultados, verifique la conexi&oacute;n con el" +
				" servidor e intente nuevamente");
	});
}

$(document).ready(function(){
	$('#myModal').modal({ show: false});
	
	$("#submitFile").click(function(event){
		event.preventDefault();
		
		$("#inputid-group").removeClass("has-error");
		$("#inputfile-group").removeClass("has-error");
		$("#msgError").hide();
		if($("#inputid").val() != "" && $("#inputfile").val() != ""){
			uploadFileToProcess();
		}else{
			$("#msgError").show();
			if($("#inputid").val() == ""){
				$("#inputid-group").addClass("has-error");
			}
			
			if($("#inputfile").val() == ""){
				$("#inputfile-group").addClass("has-error");
			}
		}
	});
	
	$("#testCases").click(function(e){
		e.stopPropagation();
		openTestCases();
	});
});