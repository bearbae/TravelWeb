document.addEventListener("DOMContentLoaded", function (){

    var addTour = document.getElementById("addTour");

    addTour.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const tour = {} ;
        formData.forEach(function (value,key){
            tour[key] = value;
        })
        console.log(tour)

        fetch(`http://localhost:8080/api/admin/tour/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tour)
        })
            .then(response => {
                if(!response.ok){
                    throw new Error("Không có phản hồi!")
                }
                return response.json();
            })
            .then(data => {
                if(data == null){
                    alert("Tour đã tồn tại! Hãy thử lại.");
                } else{
                    alert("Thêm mới thành công");
                    window.location.href = "/admin/tour"
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})