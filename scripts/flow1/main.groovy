def req = null
def payload = null


def chain() {
    id = req.getParameter("id")
    prev_state = Memory.instance.get(id)
    switch (prev_state) {
        case "username":
            Memory.instance.put(id, "password")
            break
        case "password":
            Memory.instance.put(id, "mfa")
            break
        case "mfa":
            Memory.instance.put(id, "success")
            break
        default:
            id = UUID.randomUUID().toString()
            Memory.instance.put(id, "username")
            break
    }
    if (prev_state != null) {
        ch = "?validate_" + prev_state + "+!" + Memory.instance.get(id)
        return ch
    }
    "!" + Memory.instance.get(id)

}

def init(def method, def req) {
    this.req = req
    this.method = method
    this.payload = [:]
    this.username = new BoxUsername()
    this.password = new BoxPassword()
    this.mfa = new BoxMFA()
    this.success = new BoxSuccess()
}

def post() {
    this.payload["id"] = req.getParameter("id")
    payload
}

def username() {
    username.displayInput(payload, id)
}

def validate_username() {
    username.validate(payload, id, req)
}

def password() {
    password.displayInput(payload, id)
}

def validate_password() {
    password.validate(payload, id, req)
}

def mfa() {
    mfa.displayInput(payload, id)
}

def validate_mfa() {
    mfa.validate(payload, id, req)
}

def success() {
    success.displayInput(payload, id)
}



