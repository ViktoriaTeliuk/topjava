// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
    $(":checkbox").change(function () {
        let checked = this.checked;
        let checkBox = $(this);
        $.ajax({
            url: context.ajaxUrl + "?id=" + checkBox.closest("tr").attr("id") + "&checked=" + checked,
            type: "PUT"
        }).done(function () {
            if (checked) {
                checkBox.closest("tr").removeClass("notEnabled").addClass("enabled");
                checkBox.closest("tr").css("color", "black");
            } else {
                checkBox.closest("tr").removeClass("enabled").addClass("notEnabled");
                checkBox.closest("tr").css("color", "grey");
            }
            successNoty("Changed");
        });

    });
});

function updateTable() {
    $.get(context.ajaxUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}