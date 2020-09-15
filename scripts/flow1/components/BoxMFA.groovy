import com.arturoeanton.fxemmy.exception.ChainCut

class BoxMFA{
    def engine = new groovy.text.SimpleTemplateEngine();

    def displayInput(def payload, def id) {
        payload["id"] = id
        payload["username"] = Memory.instance.getUsername(id)
        def template = this.engine.createTemplate(new File('templates/mfa.html').getText('UTF-8')).make(payload)
        template.toString()
    }

    def validate(def payload, def id, def req) {
        payload["username"] = Memory.instance.getUsername(id)
        if (req.getParameter("code") != "1") {
            Memory.instance.put(id, "mfa")
            payload["error"] = "Error in code"
            def template = this.engine.createTemplate(new File('templates/mfa.html').getText('UTF-8')).make(payload)
            throw new ChainCut(template.toString())
        }
    }
}