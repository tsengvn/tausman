package com.tsengvn.tausman.data

import com.ullink.slack.simpleslackapi.SlackAttachment

data class SlackResponse(val message : String, val attachments  : List<SlackAttachment>)