/// <reference path="../../../../typings/tsd.d.ts" />
"use strict";

var $: JQueryStatic = require("jquery");

export function getEndponts(url: string) {
  return {
    postAuth: url + "/api/auth"
  };
}

export function getEndpontsFrom(selector: string) {
  var url = $(selector).data("endpoint");
  if (url == null) {
    url = "";
  }
  return getEndponts(url);
}
