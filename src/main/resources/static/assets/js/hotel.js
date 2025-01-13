//
// Láy thông tin khách sạn

document.addEventListener("DOMContentLoaded", function (){
    fetchHotel() ;

    const searchForm = document.getElementById("form")
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang khi submit
        const nameHotelInput = document.querySelector("input[name='nameHotel']").value;
        const locationInput = document.querySelector("input[name='location']").value;
        const priceHotelInput = document.querySelector("select[name='priceHotel']").value;
        searchHotelByLocationAndPrice(nameHotelInput,locationInput, priceHotelInput); // Tìm kiếm bài viết theo dia diem vaf gia
        const searchUrl = new URL(window.location);
        if(nameHotelInput) searchUrl.searchParams.set('nameHotel', nameHotelInput);
        if(locationInput) searchUrl.searchParams.set('location', locationInput);
        if(priceHotelInput) searchUrl.searchParams.set('priceHotel', priceHotelInput);
        history.pushState(null, '', searchUrl);
    });
})
async function fetchHotel() {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/hotel/all`);
        if (!response.ok) {
            console.error("Không lấy được thông tin phòng. Hãy xem lại trong hotelUser.js");
            return;
        }
        const hotel = await response.json();
        console.log(hotel);
        displayHotel(hotel);
    } catch (error) {
        console.error("error: ", error);
    }
}


async function searchHotelByLocationAndPrice(nameHotel,location, priceHotel) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/hotel/search?nameHotel=${encodeURIComponent(nameHotel)}&location=${encodeURIComponent(location)}&priceHotel=${encodeURIComponent(priceHotel)}`);
        if (!response.ok) {
            console.error("Không tìm thấy khách sạn.");
            return;
        }
        const hotels = await response.json();
        displayHotel(hotels);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm khách sạn:", error);
    }
}
function displayHotel(hotel) {
    var hotelData = document.getElementById("hotelData");
    hotelData.innerHTML = "";
    hotel.forEach( hotel =>{
        let formattedPrice = hotel.price ? hotel.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(hotel.imageHotel);
        let timestamp = new Date().getTime();
        let row =  `
                    <tr>
                        <td>${hotel.id}</td>
                        <td>${hotel.name}</td>
                        <td>${hotel.location}</td>
                        <td>${formattedPrice}</td>
                        <td>${hotel.availableRooms}</td>
                        <td><img src="/uploadsHotel/${imageName}?t=${timestamp}" alt="${hotel.name}" style="max-width: 60%;height: auto; object-fit: cover;"  /></td>
                        <td style="width: 350px;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-box-orient: vertical;
                            -webkit-box-align: start;
                            -webkit-line-clamp: 2">${hotel.description}</td>
                        <td>
                            <button class="btn btn-view" onclick="viewRoom(${hotel.id})">Xem Phòng</button>
                        </td>
                        <td>
                            <button class="btn btn-success" onclick="editHotel(${hotel.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteHotel(${hotel.id})">Xóa</button>
                        </td>
                    </tr>
            ` ;
        hotelData.innerHTML += row ;
    })
}

// Cập nhất khách sạn
function editHotel(hotelId){
    fetch(`http://localhost:8080/api/admin/hotel/${hotelId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin khách sạn!";
            }
            return response.json();
        })
        .then(data => {
             localStorage.setItem("hotel", JSON.stringify(data));
             window.location.href = "/admin/hotel/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteHotel(hotelId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/hotel/delete/${hotelId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa Thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}

// xem phogn

//  function viewRoom(hotelId) {
//      console.log(hotelId);
//      localStorage.setItem("hotelId", JSON.stringify(hotelId));
//      fetch(`http://localhost:8080/api/admin/hotel/room/all/${hotelId}`)
//          .then(response => {
//              if(!response.ok){
//                  return "Không thể lấy thông tin phong!";
//              }
//              console.log(response.status);
//              return response.json();
//          })
//          .then(data => {
//
//              localStorage.setItem("getAllRoom", JSON.stringify(data));
//              window.location.href = "/admin/hotel/room";
//          })
//          .catch(error => console.error("Error: ",error));
// }
 function viewRoom(hotelId) {
     console.log(hotelId);
     localStorage.setItem("hotelId", JSON.stringify(hotelId));
     window.location.href = "/admin/hotel/room";
}