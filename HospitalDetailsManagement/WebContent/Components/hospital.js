$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == ""){
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateHospitalForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidhospitalIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "HospitalAPI",
		type : method,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onHospitalSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidhospitalIDSave").val("");
	$("#formHospital")[0].reset();
}


//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidhospitalIDSave").val($(this).closest("tr").find('#hidHospitalIDUpdate').val());
	$("#hospital_id").val($(this).closest("tr").find('td:eq(0)').text());
	$("#hospital_name").val($(this).closest("tr").find('td:eq(1)').text());
	$("#hospital_location").val($(this).closest("tr").find('td:eq(2)').text());
	$("#hospital_availableRooms").val($(this).closest("tr").find('td:eq(3)').text());
	$("#hospital_labs").val($(this).closest("tr").find('td:eq(4)').text());
});


//REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "HospitalAPI",
		type : "DELETE",
		data : "hospital_id=" + $(this).data("hospital_id"),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});

function onHospitalDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateHospitalForm()
{
	// CODE
	if ($("#hospital_id").val().trim() == "")
	{
		return "plese insert hospital id.";
	}
	
	// NAME
	if ($("#hospital_name").val().trim() == "")
	{
		return "please insert hospital name.";
	}
	if ($("#hospital_location").val().trim() == "")
	{
		return "please insert location of the hospital.";
	}
	
	//PRICE-------------------------------
	if ($("#hospital_availableRooms").val().trim() == "")
	{
		return "Insert number of rooms.";
	}
	
	// is numerical value
	var tmpPrice = $("#hospital_availableRooms").val().trim();
	
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for Item Price.";
	}
	
	// convert to decimal price
	$("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));
	
	// DESCRIPTION------------------------
	if ($("#hospital_labs").val().trim() == "")
	{
		return "enter description about labs";
	}
	
	return true;
}