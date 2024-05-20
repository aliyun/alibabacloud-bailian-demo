<template>
  <div class="md-layout" style="height: 100%">
    <div class="md-layout-item md-size-20"></div>
    <div class="md-layout-item">
      <div class="chat-list" ref="chatList">
        <md-list>
          <md-list-item v-for="item in messages" :key="item.index">
            <md-avatar>
              <img v-if="item.type === 'user'" src="../assets/people.png" alt="Avatar">
              <img v-else-if="item.type === 'assistant'" src="../assets/tongyi.png" alt="Avatar">
            </md-avatar>
            <div class="md-elevation-1" style="width: 100%; padding: 10px 20px 10px 20px">
              <p style="white-space: normal">
                {{ item.content }}
              </p>
            </div>
          </md-list-item>
        </md-list>
      </div>

      <div class="chat-input">
        <md-field>
          <md-textarea style="resize: none" placeholder="请输入您想问的问题" v-model="prompt" @keydown.enter="onInputClicked"></md-textarea>
          <md-button class="md-icon-button" v-on:click="onInputClicked">
            <md-icon>arrow_forward</md-icon>
<!--            <md-icon>stop</md-icon>-->
          </md-button>
        </md-field>
      </div>

      <md-snackbar :md-position="'center'" :md-duration="3000" :md-active.sync="snackbarShow" md-persistent>
        <span>{{snackbarContent}}</span>
      </md-snackbar>
    </div>
    <div class="md-layout-item md-size-20"></div>
  </div>
</template>

<script>
  import {fetchEventSource} from "@microsoft/fetch-event-source"
  export default {
    name: "Index",
    data() {
      return {
        generating: false,
        prompt: "",
        sessionId: "",
        messages: [],
        snackbarShow: false,
        snackbarContent: "",
      }
    },
    methods: {
      scrollToBottom() {
        this.$nextTick(()=>{
          const chatList = this.$refs.chatList;
          chatList.scrollTop = chatList.scrollHeight;
        });
      },

      onInputClicked() {
        if(this.prompt.length === 0) {
          console.log("prompt is empty!");
          return;
        }

        if(this.generating === true) {
          console.log("assistant generating!");
          return;
        }

        this.messages.push({index: this.messages.length, type: "user", content: this.prompt});
        this.messages.push({index: this.messages.length, type: "assistant", content: ""});

        const self = this;
        const abortController = new AbortController()
        // const url = "http://127.0.0.1:8080/v1/completions";
        const url = window.location.protocol + "//" + window.location.host + "/v1/completions";

        fetchEventSource(url, {
          method: "POST",
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            prompt: this.prompt,
            sessionId: this.sessionId,
          }),
          signal: abortController.signal,
          onmessage(msg) {
            console.log(msg.data);
            let data = JSON.parse(msg.data);

            if(data["success"] === true) {
              self.messages[self.messages.length-1].content = data["data"]["text"];

              if(self.generating === false) {
                self.generating = true;
                self.prompt = "";
              }

              if(data["data"]["finishReason"] === "stop") {
                self.sessionId = data["data"]["sessionId"];
                self.generating = false;
              }
            } else {
              self.snackbarContent = data["errorMsg"];
              self.snackbarShow = true;
            }

            self.scrollToBottom();
          },
          onerror(error) {
            console.log(error);
          },
          onclose() {
            console.log("http sse close!");
          },
        });
      },
    },
  }
</script>

<style scoped>
.md-layout-item{
  max-height: 100%;
}

.chat-list{
  min-height: 80%;
  max-height: 80%;
  overflow-y: auto
}

.chat-list::-webkit-scrollbar {
  display: none; /* 不显示滚动条 */
}
</style>
