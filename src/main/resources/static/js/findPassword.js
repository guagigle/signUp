const getNum = () => {
    const phoneNumber = document.querySelector("#phoneNumberArea div div #inputPhoneNumber").value;
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/findpw/phoneCertificate", true);
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.send(`phoneNumber=${phoneNumber}`);    
    xhr.onload = () => {
        if(xhr.status === 200) {
            alert(xhr.response);
        } else {
            alert('인증 번호를 받아오지 못했습니다')
        }
    }
}

const checkNum = () => {
    const phoneNumber = document.querySelector("#phoneNumberArea div div #inputPhoneNumber").value;
    const number = document.querySelector("#certificationArea div div input").value;
    console.log(`Your phone number is ${phoneNumber}, Your input is ${number}`);
    let data = JSON.stringify({
        "phoneNumber":""+phoneNumber+"",
        "certificateNumber":""+number+""
    });
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/confirm", true);
    xhr.setRequestHeader("Content-Type","application/json");
    xhr.send(data);
    xhr.onload = () => {
        if(xhr.response === "success") {
            alert('인증되었습니다.');
            location.href=`http://localhost:8080/renamePassword?phoneNumber=${phoneNumber}`;
        } else {
            alert('인증 번호가 다릅니다.')
        }
    }
}

const renamePassword = (phoneNumber) => {
    if(!phoneNumber) {
        alert('잘못된 접근입니다.');
        location.href='http://localhost:8080';
        return;
    }
    const newPassword = document.querySelector("#newPassword").value;
    const validCheck = document.querySelector("#validCheck").value;
    
    if(newPassword !== '' && newPassword === validCheck) {
        let data = JSON.stringify({
            "phoneNumber":""+phoneNumber+"",
            "password":""+newPassword+""
        });
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/changePassword", true);
        xhr.setRequestHeader("Content-Type","application/json");
        xhr.send(data);
        xhr.onload = () => {
            if(xhr.status === 200) {
                alert('비밀번호가 변경되었습니다. 새 비밀번호로 로그인 해주세요.');
                location.href="http://localhost:8080/login";
            } else {
                alert('실패하였습니다.')
            }
        }
    }
    else {
        alert('두 비밀번호가 다릅니다.');
    }
}