const showUserInfo = (userInfo) => {
    const userInfoArea = document.querySelector("#userInfoArea");
    userInfoArea.innerHTML = `
    <div>이메일 : ${userInfo.userEmail}</div>
    <div>전화번호 : ${userInfo.userPhoneNumber}</div>
    <div>이름 : ${userInfo.userName}</div>
    <div>닉네임 : ${userInfo.userNickName}</div>
    `
}

const logOut = () => {
    location.href = 'http://localhost:8080/';
}