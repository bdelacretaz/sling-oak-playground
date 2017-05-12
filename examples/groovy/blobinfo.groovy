// Gets the Blob info of a specific JCR Property
// (TODO use a request parameter for the path, can groovy console do that?)

import org.apache.sling.jcr.api.SlingRepository
import javax.jcr.Session
import javax.jcr.Property
import javax.jcr.Value
import org.apache.jackrabbit.oak.api.Blob
import org.apache.jackrabbit.oak.plugins.value.ValueImpl

SlingRepository repo = sling.getService(SlingRepository.class)
Session s = null
try {
	String path = "/tmp/foo.jpg/jcr:content/jcr:data"
    s = repo.loginAdministrative(null)
    Property p = s.getProperty(path)
	Value v = p.getValue()
	
    println "Path=" + p.getPath()
	
	if(v == null) {
		println("Value is null")
    } else {
		Blob b = ValueImpl.getBlob(v)
		if(b == null) {
			println("Blob is null")
        } else {
			println "ContentIdentity=" + b.getContentIdentity()
			println "Reference=" + b.getReference()
        }
    }
} finally {
    s?.logout()
}
