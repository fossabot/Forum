<script lang="ts">
    let fileInput:any
    var myHeaders = new Headers();
myHeaders.append("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZGdodGh0cnRociIsImlhdCI6MTY3ODAzOTQ4MSwiZXhwIjoxNjc4MTI1ODgxfQ.oO4Q8UvjtjPwJ_2ab7uVS4O36pXY6UXuo8bfGlrvBvE");

var formdata = new FormData();
formdata.append("file", fileInput.files[0], "icon.png");

var requestOptions:any = {
  method: 'GET',
  headers: myHeaders,
  body: formdata,
  redirect: 'follow'
};

fetch("http://127.0.0.1:8080/api/v1/file/profile/86fea473-9516-4314-9af6-7b41ee840f42", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
</script>