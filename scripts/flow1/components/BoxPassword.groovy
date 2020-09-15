import com.arturoeanton.fxemmy.exception.ChainCut

class BoxPassword{
    def engine = new groovy.text.SimpleTemplateEngine();

    def displayInput(def payload, def id) {
        payload["id"] = id
        payload["username"] = Memory.instance.getUsername(id)
        def template = this.engine.createTemplate(new File('templates/password.html').getText('UTF-8')).make(payload)
        template.toString()
    }

    def validate(def payload, def id, def req) {
        if (req.getParameter("password") != "qwerty") {
            Memory.instance.put(id, "password")
            payload["username"] = Memory.instance.getUsername(id)
            payload["error"] = "Error password"
            def template = this.engine.createTemplate(new File('templates/password.html').getText('UTF-8')).make(payload)
            throw new ChainCut(template.toString())
        }
        print("user save")
    }
}