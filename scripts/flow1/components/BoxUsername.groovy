class BoxUsername{
    def engine = new groovy.text.SimpleTemplateEngine();

    def displayInput(def payload, def id) {
        payload["id"] = id
        def template = this.engine.createTemplate(new File('templates/username.html').getText('UTF-8')).make(payload)
        template.toString()
    }

    def validate(def payload, def id, def req) {
        Memory.instance.putUsername(id, req.getParameter("username"))
        payload
    }
}