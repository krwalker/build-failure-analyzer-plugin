/*
 * The MIT License
 *
 * Copyright 2012 Sony Mobile Communications AB. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.sonyericsson.jenkins.plugins.bfa.model.FailureCause

import com.sonyericsson.jenkins.plugins.bfa.model.FailureCause;
import com.sonyericsson.jenkins.plugins.bfa.CauseManagement;
import com.sonyericsson.jenkins.plugins.bfa.PluginImpl;
import hudson.Functions

def f = namespace(lib.FormTagLib)
def l = namespace(lib.LayoutTagLib)
def j = namespace(lib.JenkinsTagLib)

l.layout(permission: PluginImpl.UPDATE_PERMISSION) {
  l.header(title: _("Failure Cause Management") + " " + my.getName())

  def management = my.getAncestorCauseManagement();
  def imageUrl = PluginImpl.getImageUrl("256x256", "information.png");

  l.side_panel() {
    if (!management.isUnderTest()) {
      include(management.getOwner(), "sidepanel.jelly")
    }
  }

  l.main_panel() {
    div(style: "width: 256px; "
            + "height: 256px;"
            + "opacity:0.2;"
            + "right:-10px;"
            + "top: 50px;"
            + "position: absolute;"
            + "z-index: -100;"
            + "background-image: url(\'" + imageUrl + "');") {}
    h1(_("Failure Cause"))
    div(style: "width: 70%") {
      f.form(action: "configSubmit", method: "POST", name: "causeForm") {
        f.invisibleEntry() {
          f.textbox(field: "id", value: my.getId())
        }
        f.entry(title: _("Name"), field: "name") {
          f.textbox(value: my.getName(), checkUrl: "'checkName?value='+escape(this.value)")
        }
        f.entry(title: _("Description"), field: "description") {
          f.textarea(value: my.getDescription(), checkUrl: "'checkDescription?value='+escape(this.value)")
        }
        f.section(title: _("Indications")) {
          f.block {
            f.hetero_list(
                    name:"indications",
                    hasHeader:true,
                    descriptors:management.getIndicationDescriptors(),
                    items:my.getIndications(),
                    addCaption:_("Add Indication"),
                    deleteCaption:_("Delete Indication"))
          }
        }
        f.block {
          div(style: "margin-top: 10px")
          f.submit(value: _("Save"))
        }
      }
    }
  }
}
