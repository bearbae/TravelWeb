document.addEventListener("DOMContentLoaded", function (){
    var serviceTour = JSON.parse(localStorage.getItem("serviceTour"));
    let formattedPricePerAdult = serviceTour.pricePerAdult ? serviceTour.pricePerAdult.toLocaleString('vi-VN'): 'N/A';
    let formattedPricePerChild = serviceTour.pricePerChild ? serviceTour.pricePerChild.toLocaleString('vi-VN'): 'N/A';

    if(serviceTour){
        document.getElementById("timeToGo").value = serviceTour.timeToGo ;
        document.getElementById("timeToBack").value = serviceTour.timeToBack ;
        document.getElementById("address1").value = serviceTour.address1 ;
        document.getElementById("time1").value = serviceTour.time1 ;
        document.getElementById("address2").value = serviceTour.address2 ;
        document.getElementById("time2").value = serviceTour.time2 ;
        document.getElementById("address3").value = serviceTour.address3 ;
        document.getElementById("time3").value = serviceTour.time3 ;
        document.getElementById("pricePerAdult").value = formattedPricePerAdult ;
        document.getElementById("pricePerChild").value = formattedPricePerChild ;
        document.getElementById("code").value = serviceTour.code ;
        document.getElementById("vehicle").value = serviceTour.vehicle ;
    }
    var updateService = document.getElementById("updateService");
    updateService.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const data = {} ;
        formData.forEach(function (value,key){
            data[key] = value;
        })
        console.log(data);
        fetch(`http://localhost:8080/api/serviceTour/update/${serviceTour.id}`, {
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
                    window.location.href = "/admin/tour/serviceTour";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Dich vu Tour này đã tồn tại. Hãy thử lại!");
            })
    })
})

