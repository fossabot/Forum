<script lang="ts">
  import { Form, FormGroup, Input} from "sveltestrap";
  import "bootstrap/dist/css/bootstrap.min.css";
  import { Button } from "sveltestrap";
  import { token, cookie_name } from "../../lib/Login/login.js";
  import { getCookie } from "../../lib/functions";
  import { onMount } from "svelte";
  import  Error  from "../../lib/Error/error.svelte"
  import { goto } from "$app/navigation";
  let post_title: string;
  let post_body: string;
  let post_id: string;
  let tokenValue: string;
  let cookie_name_value: string;
  let error = false;
  let file:any;
  let image_file:any;
  let ip:string

  async function get_server_ip() {
    ip = "http://"+location.hostname+":8080/"
  }

  async function checkLoggedIn() {
    cookie_name_value = await getCookie("username");
    cookie_name.set(cookie_name_value);
    tokenValue = await getCookie("tokenValue");
    token.set(tokenValue);
  }

  onMount(async () => {
    await get_server_ip();
    await checkLoggedIn();
    await subStores();
  });

  async function subStores() {
    token.subscribe((value: string) => {
      tokenValue = value;
      console.log(tokenValue);
    });

    cookie_name.subscribe((value: string) => {
      cookie_name_value = value;
      console.log(cookie_name_value);
    });
  }

  async function post() {
    error = false;
    const res = await fetch(ip + "api/v1/post", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + tokenValue,
      },
      body: JSON.stringify({
        title: post_title,
        content: post_body,
      }),
    });
    if (!res.ok) {
      error = true;
    }
    const json = await res.json();
    await upload_image(json.id);
    post_id = json.id;
    return res.json();
  }

  const handleFileChange = (event:any) => {
    file = event.target.files[0];
  };

  async function upload_image (post_id:string) {
  
    const formData = new FormData();
    formData.append('file', file);
    const res = await fetch(ip +"api/v1/file/post/" + post_id,{
      method: 'POST',
      body: formData,
      headers: { 
        Authorization: "Bearer " + tokenValue,
      },
    });
    console.log(res)
    await goto("/post/"+post_id)
  };


</script>

{#if error}
<Error></Error>

{/if}

<div class="container">
  <div class="alert alert-dark" role="alert">
    <Form>
      <FormGroup floating label="Titel">
        <Input placeholder="Titel" required bind:value={post_title} />
      </FormGroup>

      <FormGroup>
        <div class="form-floating">
          <textarea
            class="form-control"
            placeholder="Body"
            bind:value={post_body}
            id="floatingTextarea2"
            style="height: 100px"
          />
          <label for="floatingTextarea2">Text</label>
        </div>
      </FormGroup>
      <FormGroup>
        <Input type="file" name="file" id="AvatarFile" bind:this={image_file} on:change={handleFileChange}/>
      </FormGroup>
      <Button color="primary" on:click={post}>Post</Button>
    </Form>
  </div>
  
</div>

<style>
  .container {
    max-width: 700px;
    margin: 0 auto;
    padding: 2rem;
    text-align: center;
  }
  textarea {
    width: 100%;
    height: 300px;
  }

  .container {
    padding: 10px 16px;
    margin: 40px auto;
    max-width: 800px;
  }
</style>
