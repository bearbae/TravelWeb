document.addEventListener("DOMContentLoaded", function (){
    var tourIdForService = JSON.parse(localStorage.getItem("tourIdForService"));
    var addServiceTour = document.getElementById("addServiceTour");

    addServiceTour.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const data = {} ;
        formData.forEach(function (value,key){
            data[key] = value;
        })
        fetch(`http://localhost:8080/api/serviceTour/add/${tourIdForService}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if(!response.ok){
                    throw new Error("Không có phản hồi!")
                }
                return response.json();
            })
            .then(data => {
                if(data){
                    window.location.href = "/admin/tour/serviceTour"
                } else{
                    alert("Loix")
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})