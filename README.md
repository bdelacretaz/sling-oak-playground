# sling-oak-playground
Sling Launchpad configured to play with Oak using the Sling Groovy script console

See Chetan's Groovy script examples at https://gist.github.com/chetanmeh/3748614

Here's a slightly one that works on this setup with the curl loop shown below:

    //Logs in using SlingRepository service and opens a session
    
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
    
And here's the curl loop that runs it, assuming it's at `/tmp/xyzzy.groovy`

    while true
      do curl -u admin:admin \
        -Flang=groovy \
        -Fcode=@/tmp/xyzzy.groovy \
        http://localhost:8080/system/console/scriptconsole
      sleep 1
      clear
    done


    
    

