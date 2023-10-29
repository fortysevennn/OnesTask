package com.tugaypamuk.app.onestask.services

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


data class Email(
    val subject: String,
    val body: String,
    val to: String
)

class EmailService(
    private val smtpServer: String,
    private val username: String,
    private val password: String,
    private val port : String,
    private val mailFrom : String
) {
    fun sendEmail(email: Email) {
        val properties = Properties()
        properties.put("mail.smtp.host", smtpServer)
        properties.put("mail.smtp.port", port)
        properties.put("mail.smtp.auth", "true")
        properties.put("mail.smtp.starttls.enable", "true")

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(mailFrom))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.to))
            message.subject = email.subject
            message.setText(email.body)

            Transport.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace()

        }
    }
}