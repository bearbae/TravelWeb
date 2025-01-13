document.addEventListener("DOMContentLoaded", function (){
    var tourData = JSON.parse(localStorage.getItem("AdminEditTour"));
    let formattedPrice = tourData.price ? tourData.price.toLocaleString('vi-VN'): 'N/A';
    if(tourData){
        document.getElementById("typeTour").value = tourData.typeTour ;
        document.getElementById("address").value = tourData.address ;
        document.getElementById("price").value = formattedPrice ;
        document.getElementById("rateStar").value = tourData.rateStar ;
        document.getElementById("titleTour").value = tourData.titleTour ;
        document.getElementById("quantityComment").value = tourData.quantityComment;
        document.getElementById("booked").value = tourData.booked;
        document.getElementById("highlight").value = tourData.highlight;
        document.getElementById("content").value = tourData.content;
    }
    var updateTour = document.getElementById("updateTour");
    updateTour.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const data = {} ;
        formData.forEach(function (value,key){
            data[key] = value;
        })
        console.log(data);
        fetch(`http://localhost:8080/api/admin/tour/update/${tourData.id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        }).then(response =>{
            if(!response.ok){
                throw new Error("ErrorUpdate");
            }
            return response.json();
        })
            .then(data => {
                if(data){
                    alert("Cập nhật thành công tour với id = " + data.id);
                    localStorage.removeItem("updateTour");
                    window.location.href = "/admin/tour";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Tour này đã tồn tại. Hãy thử lại!");
            })
    })
})

