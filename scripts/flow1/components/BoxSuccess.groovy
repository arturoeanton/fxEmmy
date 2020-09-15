class BoxSuccess{
    def engine = new groovy.text.SimpleTemplateEngine();

    def displayInput(def payload, def id) {
        payload["username"] = Memory.instance.getUsername(id)
        def template = engine.createTemplate(new File('templates/success.html').getText('UTF-8')).make(payload)
        template.toString()
    }

    def validate(def payload, def id, def req) {
        payload
    }
}