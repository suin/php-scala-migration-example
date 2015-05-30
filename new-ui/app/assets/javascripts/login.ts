/// <reference path="../../../typings/tsd.d.ts" />
/// <reference path="./modules/endpoints.ts" />
"use strict";

var $: JQueryStatic = require("jquery");
import Endpoints = require("./modules/endpoints");

$(() => {
  var endpoints = Endpoints.getEndpontsFrom("#mount-login-form");
  $("#mount-login-form").replaceWith(require("../templates/login-form.jade")());
  $(document).on("submit", "#login-form", (e) => {
    e.preventDefault();
    var username = $("#login-form input[name=username]").val();
    var password = $("#login-form input[name=password]").val();
    var data = {username: username, password: password};
    $.ajax({
      type: "POST",
      url: endpoints.postAuth,
      contentType: "application/json",
      data: JSON.stringify(data)
    }).done((data) => {
      alert("welcome " + data.username + "!");
      location.href = "/";
    }).fail((err) => {
      alert(err.responseJSON.message);
    });
  });
});
