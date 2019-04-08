// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});

function clearForm(oForm) {
    let elements = oForm.elements;
    oForm.reset();
    for (i = 0; i < elements.length; i++) {
        field_type = elements[i].type.toLowerCase();
        switch (field_type) {
            case "date":
            case "time":
                elements[i].value = "";
                break;
        }
    }
    updateTable();
}

function updateTable() {
    $.get(context.ajaxUrl + "?startDate=" + $("input[name=startDate]").val() + "&endDate=" + $("input[name=endDate]").val()
        + "&startTime=" + $("input[name=startTime]").val() + "&endTime=" + $("input[name=endTime]").val(), function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}


