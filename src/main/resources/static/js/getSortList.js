$(document).ready(function () {
        function getList(sortType, priceFilter) {

            $.ajax({
                type: "GET",
                url: "api/apartment/all?sort=" + sortType +
                "&pricefilter=" + priceFilter,
                success: function (result) {
                    if (result.status === "Done") {
                        $('#tbodyId tr').empty();
                        $.each(result.data, function (i, apart) {
                            var customer = "<tr><td>" +
                                apart.name +
                                "</td><td>" +
                                apart.description +
                                "</td><td>" +
                                apart.price +
                                "</td><td><a href=\"" +
                                "/buyApartment/" +
                                apart.id +
                                "\" class=\"btn btn-primary\">" +
                                "Detail" +
                                "</a></td></tr>";
                            $('#tbodyId').append(customer)
                        });
                        console.log("Success: ", result);
                    } else {
                        console.log("Fail: ", result);
                    }
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
        }

        getList("name_sort", 0)

        $("#sortByName").click(function () {
                getList("name_sort", "0")
            }
        )

        $("#sortByPrice").click(function () {
                getList("price_sort", "0")
            }
        )

        $("#filterMoreButton").click(function () {
                getList("price_Less_filter", $('input[name="filterLessField"]').val())
            }
        )
    }
)

// Sorting table
// $(document).ready( function () {
//     var table = $('#apartmentTable').DataTable();
// } );