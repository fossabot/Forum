<script lang="ts">
  import "bootstrap/dist/css/bootstrap.min.css";
  import {
    Navbar,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink,
    Button,
  } from "sveltestrap";
  import { own_user_id } from "../lib/Login/login";
  import { goto } from "$app/navigation";
  let own_user_id_value: string;

  own_user_id.subscribe((value: string) => {
    own_user_id_value = value;
  });

  async function signOut() {
    document.cookie.split(";").forEach(function (c) {
      document.cookie = c
        .replace(/^ +/, "")
        .replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); // Sets every cookie as expired to delete them
    });
    await goto("/");
    location.reload();
  }

  async function gotoProfile() {
    const path = window.location.pathname.split("/");
    const userid = path[path.length-1]; // Get userid from url path
    if(userid != own_user_id_value) {
      await goto("/profile/"+own_user_id_value)
      location.reload();
    }
    
  }

</script>

<Navbar>
  <NavbarBrand href="/">GHSE-Board</NavbarBrand>

  <Nav class="ms-auto">
    {#if own_user_id_value != undefined && own_user_id_value != "undefined"}
      <NavItem>
        <NavLink href={"/profile/" + own_user_id_value } on:click={gotoProfile}>Profil</NavLink>
      </NavItem>
      <NavItem>
        <NavLink href="/create_post">Post erstellen</NavLink>
      </NavItem>
      <NavItem>
        <NavLink href="/search">Suche</NavLink>
      </NavItem>
      <NavItem>
        <Button color="primary" on:click={signOut}>Abmelden</Button>
      </NavItem>
    {/if}
  </Nav>
</Navbar>

<slot />
