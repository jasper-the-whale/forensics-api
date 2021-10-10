package com.forensics.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

// Getting these from application.yaml
// Ideal would want them not to be stored in a text file, better stored in Vault
@ConfigurationProperties(prefix = "authentication")
data class Authentication(
    var emails: List<String> = listOf()
)
