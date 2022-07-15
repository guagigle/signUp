const handleClick = (radio) => {
    const inputs = document.getElementsByName('loginInfo');
    inputs.forEach(input => {
        document.querySelector(`#${input.value}`).disabled = true;
    });
    const inputArea = document.querySelector(`#${radio.value}`);
    inputArea.disabled = false;
}