package com.tsengvn.tausman.data

import com.atlassian.jira.rest.client.domain.Issue
import java.io.Serializable

data class JiraIssue(var key : String = "", var summary : String = "", var assignee : String? = null,
                     var project : String? = null, var description : String? = null) : Serializable {

    constructor(issue: Issue) : this(issue.key, issue.summary, issue.assignee?.displayName, issue.project?.name, issue.description)

}
