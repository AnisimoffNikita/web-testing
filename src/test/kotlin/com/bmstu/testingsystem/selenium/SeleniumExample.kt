package com.bmstu.testingsystem.selenium


class SeleniumExample {

    /*private val config: SeleniumConfig
    private val url = "http://www.baeldung.com/"

    val title: String
        get() = this.config.driver.title

    val isAuthorInformationAvailable: Boolean
        get() = this.config.driver
                .findElement(By.cssSelector("article > .row > div"))
                .isDisplayed

    init {
        config = SeleniumConfig()
        config.driver.get(url)
    }

    fun closeWindow() {
        this.config.driver.close()
    }

    fun getAboutBaeldungPage() {
        closeOverlay()
        clickAboutLink()
        clickAboutUsLink()
    }

    private fun closeOverlay() {
        val webElementList = this.config.driver.findElements(By.tagName("a"))
        webElementList?.stream()?.filter {
            webElement -> "Close".equals(webElement.getAttribute("title"), ignoreCase = true) }
                ?.filter{ it.isDisplayed() }
                ?.findAny()
                ?.ifPresent{ it.click() }
    }

    private fun clickAboutLink() {
        this.config.driver.findElement(By.partialLinkText("About")).click()
    }

    private fun clickAboutUsLink() {
        val builder = Actions(config.driver)
        val element = this.config.driver.findElement(By.partialLinkText("About Baeldung."))
        builder.moveToElement(element).build().perform()
    }*/
}