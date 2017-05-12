// Adds an nt:file to the content repository
// Source file must exist on disk

import org.apache.sling.jcr.api.SlingRepository
import javax.jcr.Session
import javax.jcr.Node
import javax.jcr.Property
import javax.jcr.Binary

SlingRepository repo = sling.getService(SlingRepository.class)
Session s = null
InputStream is = null
try {
    s = repo.loginAdministrative(null)
    Node n = s.getNode('/')
	String path = "p4312.png"
    Node n2 = n.hasNode(path) ? n.getNode(path) : n.addNode(path,"nt:file")
	String content = "jcr:content"
    Node contentNode = n2.hasNode(content) ? n2.getNode(content) : n2.addNode(content,"nt:resource")
    is = new FileInputStream(new File('/tmp/foo.jpg'))
    Binary b = s.valueFactory.createBinary(is)
    contentNode.setProperty("jcr:data",b)
    s.save()

    println new Date()
    println "Path=" + contentNode.path 
    println "Binary size=" + b.getSize()
} finally {
    is?.close()
    s?.logout()
}
