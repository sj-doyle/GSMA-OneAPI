package org.gsm.oneapi.foundation;

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Not in current use - initial model for JSON request/response
 */
@Deprecated
public class ComHelper {

	static Logger logger=Logger.getLogger(ComHelper.class);
	static final boolean DEBUG_ENABLED = logger.isDebugEnabled();
	static final boolean INFO_ENABLED = logger.isInfoEnabled();
	static final boolean WARN_ENABLED = logger.isEnabledFor(Level.WARN);
	static final boolean ERROR_ENABLED = logger.isEnabledFor(Level.ERROR);
	
	public static final String baseurl="https://";
	private static final String username="";
	private static final String password="";
	
	@Deprecated
	public void XMLTextEncoder(StringBuffer destination, String tagname, String value, boolean usecdata) {
		if (value!=null && value.length()>0) {
			if (usecdata) {
				destination.append("<"+tagname+"><![CDATA["+value.trim()+"]]></"+tagname+">\n");
			} else {
				destination.append("<"+tagname+">"+value.trim()+"</"+tagname+">\n");
			}
		}
	}
	
	@Deprecated
	public static String base64Encode(String s) {
	    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	    Base64OutputStream out = new Base64OutputStream(bOut);
	    try {
	      out.write(s.getBytes());
	      out.flush();
	    } catch (IOException exception) {
	    }
	    return bOut.toString();
	}
	
	@Deprecated
	public String ReadResponse (String location, long lastmodified, boolean logResponse, boolean canCompress) {

		if (INFO_ENABLED) logger.info("ComHelper.ReadResponse - getting response from "+location);
		long start=System.currentTimeMillis();
				
		String rv=null;
		try {	
			URL url=new URL(location);
			URLConnection conn=url.openConnection();
			if (canCompress) conn.addRequestProperty("Accept-Encoding","gzip");
			
	        String input = username + ":" + password;
	        String encodedusernamepassword = base64Encode(input);
	        conn.setRequestProperty("Authorization", "Basic " + encodedusernamepassword);

			if (lastmodified>0) conn.setIfModifiedSince(lastmodified);

			//System.out.println("Setting request property for gzip encoding");
//			long resourceModified=conn.getLastModified();
			String encoding=conn.getContentEncoding();

			StringBuffer read=new StringBuffer();
			int maxlen=8192;
			
			int nread;
			String mode="Text";
			
			if (encoding!=null && encoding.indexOf("gzip")!= -1) {
//				System.out.println("ZIP read of "+location);
				GZIPInputStream zipIn = new GZIPInputStream(conn.getInputStream());
				
				byte[] ba=new byte[maxlen];
				
				while ((nread=zipIn.read(ba, 0, maxlen))!= -1) {
					read.append(new String(ba, 0, nread));
				}
				mode="ZIP";
			} else {
				//System.out.println("Last modified "+resourceModified+" local "+lastmodified+" differ? "+(lastmodified!=resourceModified));
				InputStream in=conn.getInputStream();
				InputStreamReader isr=new InputStreamReader(in);
				
				char[] ca=new char[maxlen];
				
				while ((nread=isr.read(ca))!= -1) {
					read.append(ca, 0, nread);
				}				
			}
			rv=read.toString().trim();
			
			long end=System.currentTimeMillis();
			if (INFO_ENABLED && rv!=null) logger.info("ComHelper.ReadResponse - read "+rv.length()+" characters. Mode="+mode+" Time="+(end-start)+" mS");
			
			if (logResponse) {
				System.err.println("ComHelper.ReadResponse Request to "+location);
				if (rv == null || rv.length()==0) {
					System.err.println("*** No response received ***");
					if (WARN_ENABLED) {
						logger.warn("ComHelper.ReadResponse Request to "+location);
						logger.warn("*** No response received ***");
					}
				} else {
					System.err.println(rv);
				}
			}

		} catch (IOException e) {
			if (ERROR_ENABLED) logger.error("ComHelper.ReadResponse - IOException reading "+location+" "+e.getMessage()+" - "+e.getCause(),e);
        	//System.err.println(e); 
        	//e.printStackTrace();
      	}
      	
      	return rv;
	}
	



	@Deprecated
	public String SendAndReadResponse (String location, String requestbody, long lastmodified, boolean logRequest, boolean logResponse, boolean canCompress) {
		
		String response=null;
		if (location!=null && requestbody!=null) {
			long start=System.currentTimeMillis();
			
			try {	

				if (INFO_ENABLED) logger.info("ComHelper.SendAndReadResponse - posting XML request to "+location);
				
				if (logRequest) {
					System.err.println("ComHelper.SendAndReadResponse - sending request to "+location);
					if (requestbody.trim().length()==0) {
						System.err.println("*** No request body ***");
						if (WARN_ENABLED) {
							logger.warn("ComHelper.SendAndReadResponse - sending request to "+location);
							logger.warn("*** No request body ***");
						}
					} else {
						System.err.println(requestbody.trim());
					}
				}
			
				URL url=new URL(location);
				
				StringBuffer requestbuf=new StringBuffer();
				requestbuf.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
				requestbuf.append(requestbody);
	
				URLConnection conn=url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				if (canCompress) conn.setRequestProperty("Accept-Encoding","gzip");
				conn.setRequestProperty("Content-Type","text/xml");
				
		        String input = username + ":" + password;
		        String encodedusernamepassword = base64Encode(input);
		        conn.setRequestProperty("Authorization", "Basic " + encodedusernamepassword);


				if (lastmodified>0) conn.setIfModifiedSince(lastmodified);

//				InputStream is=null;
				OutputStream os=null;		
				
				os=conn.getOutputStream();		

//				String xmlbuf=null;
//				boolean responseAvailable=false;

				if (os!=null) {
					String outputstring=requestbuf.toString();
					
					//System.out.println("SendAndReadResponse. Sending:"+outputstring);
						
					byte[] ba=outputstring.getBytes();
					os.write (ba);
					os.flush();
					os.close();

					String encoding=conn.getContentEncoding();
					
					InputStream in=conn.getInputStream();
					String mode="Text";
					
					if (in!=null) {
						StringBuffer readbuf=new StringBuffer();
						
						int maxlen=8192;
						
						int nread;
						
						if (encoding!=null && encoding.indexOf("gzip")!= -1) {
							GZIPInputStream zipIn = new GZIPInputStream(in);
							ba=new byte[maxlen];
							while ((nread=zipIn.read(ba, 0, maxlen))!= -1) {
								readbuf.append(new String(ba, 0, nread));
							}
							mode="ZIP";
						} else {						
							InputStreamReader isr=new InputStreamReader(in);
							char[] ca=new char[maxlen];
							
							while ((nread=isr.read(ca))!= -1) {
								readbuf.append(ca, 0, nread);
							}
						}
	
						response=readbuf.toString();
						response=response.trim(); 					
			
						long end=System.currentTimeMillis();			
						if (INFO_ENABLED) logger.info("ComHelper.SendAndReadResponse - read "+response.length()+" characters. Mode="+mode+" In "+(end-start)+" mS");

						if (logResponse) {
							System.err.println("ComHelper.SendAndReadResponse Request to "+location);
							if (response.length()==0) {
								System.err.println("*** No response received ***");
								if (WARN_ENABLED) {
									logger.warn("ComHelper.SendAndReadResponse Request to "+location);
									logger.warn("*** No response received ***");
								}
							} else {
								System.err.println(mode+" Response read = "+response);
							}
						}
						
					} else {
						if (WARN_ENABLED) logger.warn("ComHelper.SendAndReadResponse - no response available");
					}
				}
			} catch (IOException e) {
				if (ERROR_ENABLED) logger.error("ComHelper.SendAndReadResponse - IOException reading "+location+" "+e.getMessage()+" - "+e.getCause());
	      	}
	 	}
      	
//		if (DEBUG_ENABLED) logger.debug("Leaving method");
      	return response;
	}
	
	@Deprecated
	public Document Deconstruct(String xmldata) {
		Document rv=null;
		if (xmldata!=null) {
			String xmlbuf=xmldata.trim();
			if (xmlbuf.length()>0) {
				InputSource is=new InputSource(new StringReader(xmlbuf));
				DocumentBuilderFactory f=null;
				DocumentBuilder p=null;
	
				f = DocumentBuilderFactory.newInstance();
			
				try {
					p = f.newDocumentBuilder();
				} catch (ParserConfigurationException ex) {
					if (ERROR_ENABLED) logger.error("ParserConfigurationException "+ex.toString());
				}
				
//				if (DEBUG_ENABLED) logger.debug("Parser configured");
			
				boolean parsed=false;
				if (p!=null) {
					try {
						rv=p.parse(is);
						if (rv!=null) parsed=true;
//						if (DEBUG_ENABLED && rv != null) logger.debug("Parse successful");
					} catch (SAXParseException ex) {
						if (ERROR_ENABLED) logger.error("SAX Parse Error "+ex.toString()+" at line "+ex.getLineNumber());
					} catch (SAXException ex) {
						if (ERROR_ENABLED) logger.error("SAX Error "+ex.toString());
					} catch (Exception ex) {
						if (ERROR_ENABLED) logger.error("XML Error "+ex.toString());
					}
					if (!parsed) {
						if (ERROR_ENABLED) logger.error("Parsing failed for \n"+xmldata);
					}
				}
			}
		} 
//		if (DEBUG_ENABLED) logger.debug("Leaving method");
		return rv;
	}

	private SimpleDateFormat sdfdate=null, sdftime=null;
	
	private SimpleDateFormat setsdf() {
		if (sdfdate==null) {
			try {
				sdfdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdftime=new SimpleDateFormat("HH:mm:ss");
			} catch (Exception e) {
				if (ERROR_ENABLED) logger.error("Caught exception creating SDF");
			}
		}
		return sdfdate;
	}
	
	@Deprecated
	public static java.util.Date ISO8601DateFromString(String formatted) {
		java.util.Date rv=null;
		if (formatted!=null) {
			//try {
				rv=DateParser.parse(formatted);
			//} catch (InvalidDateException ide) {
			//	if (ERROR_ENABLED) logger.error("ComHelper.ISO8601DateFromString caught InvalidDateException "+ide.getMessage()+" parsing "+formatted);
			//}
		}
		return rv;
	}

	@Deprecated
	public static String ISO8601DateToString(java.util.Date dt) {
		String rv=null;
		if (dt!=null) {
			rv=DateParser.getIsoDate(dt);
		}
		return rv;
	}
	
	@Deprecated
	public Calendar parseDate(String srcdate) {
		Calendar rv=null;
		if (setsdf()!=null && srcdate!=null) {
			try {
				java.util.Date parsed=sdfdate.parse(srcdate);
				if (parsed!=null) {
					rv=Calendar.getInstance();
					if (rv!=null) {
						rv.setTime(parsed);
					}
				}
			} catch (java.text.ParseException e) {
				if (ERROR_ENABLED) logger.error("ComHelper.parseDate - ouldn't parse "+srcdate);
			}
		}
		return rv;
	}

	@Deprecated
	public Calendar parseTime(String srctime) {
		Calendar rv=null;
		if (setsdf()!=null && srctime!=null) {
			try {
				java.util.Date parsed=sdftime.parse(srctime);
				if (parsed!=null) {
					rv=Calendar.getInstance();
					if (rv!=null) {
						rv.setTime(parsed);
					}
				}
			} catch (java.text.ParseException e) {
				if (ERROR_ENABLED) logger.error("ComHelper.parseTime - couldn't parse "+srctime);
			}
		}
		return rv;
	}

	@Deprecated
	public char getChar(String s){
		char rv='\0';
		if (s!=null && s.length()>0) rv=s.charAt(0);
		return rv;
	}
		
	@Deprecated
	public String RetrieveAttributeValue(Node p, String attName) {
		String rv=null;
		if (p!=null) {
			NamedNodeMap nm=p.getAttributes();
			if (nm!=null) {
				Node attr=nm.getNamedItem(attName);
				if (attr!=null) rv=RetrieveNodeValue(attr);
			}
		}
		return rv;
	}
	
	@Deprecated
	public String RetrieveNodeValue(Node p) {
		String rv=null;
		NodeList nodes;
		Node n;
		int i;
		boolean found=false;
				
		if (p != null) {
			nodes = p.getChildNodes();

			for (i = 0; i < nodes.getLength() && !found; i++) {
				n = nodes.item(i);
				if (n != null) {
					int nodeType=n.getNodeType();
					if (nodeType==Node.TEXT_NODE || nodeType==Node.CDATA_SECTION_NODE) {
						String text = n.getNodeValue();
//						Character ch=new Character('S');
						int j, length=text.length();
						boolean whitespace=true;
						for (j=0; j < length && whitespace; j++) {
						if (!Character.isWhitespace(text.charAt(j)))
							whitespace=false;
						}
						if (text.length() > 0 && !whitespace) {
							rv=text;
							found=true;
						}
					} 
				}
			}
		}
		return rv;
	}
	
	@Deprecated
	public Node FindSubNode(Node p, String elementname) {
		Node found=null, n;
		NodeList nodes;
		int i;
		String curname;
				
		
		if (p != null) {
			nodes = p.getChildNodes();
			for (i = 0; i < nodes.getLength() && found==null; i++) {
				n = nodes.item(i);
				if (n != null) {
					if (n.getNodeType()==Node.ELEMENT_NODE) {
						curname=n.getNodeName();
						if (curname.equalsIgnoreCase(elementname)) {
							found=n;
						}
					}
				}
			}
		}

		return found;
	}

	@Deprecated
	public ArrayList<Node> FindSubNodes(Node p, String elementname) {
		Node n;
		NodeList nodes;
		int i;
		String curname;
		ArrayList<Node> rv=null;
				
		
		if (p != null) {
			nodes = p.getChildNodes();
			for (i = 0; i < nodes.getLength(); i++) {
				n = nodes.item(i);
				if (n != null) {
					if (n.getNodeType()==Node.ELEMENT_NODE) {
						curname=n.getNodeName();
						if (curname.equalsIgnoreCase(elementname)) {
							if (rv==null) {
								rv=new ArrayList<Node>();
							} 
							rv.add(n);
						}
					}
				}
			}
		}

		return rv;
	}

	@Deprecated
	public String RetrieveSubnodeValue(Node p, String elementname){
		return RetrieveNodeValue(FindSubNode(p, elementname));
	}

	@Deprecated
	private void SetValueMethod(String nodename, int matchedindex, String value) {}
	@Deprecated
	private void NumAttachmentsMethod(int numAttachments) {}
	@Deprecated
	private void AddAttachmentMethod(int AttachmentID, String CustomerAttachmentKey, int AttachmentNumber, String AttachmentCreated, String AttachmentType, String AttachmentData,
									 String AttachmentMIME, int AttachmentWidth, int AttachmentHeight, int AttachmentSize, int AttachmentDuration,
									 String AttachmentStatus, String AttachmentRating, String AttachmentCaption) {}	
	
	@Deprecated
	public boolean process(Document source, String rootname, String[] expectedtags, boolean acceptsAttachments) {
		Element root=source.getDocumentElement();
		boolean processed=false;
		
		if (root!=null && rootname!=null && rootname.equals(root.getTagName()) && expectedtags!=null) {
			NodeList children=root.getChildNodes();
			processed=true;
			
			if (children!=null) {
				int i, count=children.getLength();
				int numtags=expectedtags.length;
			
				for (i=0; i<count; i++) {
					Node current=children.item(i);
					if (current!=null) {
						short type=current.getNodeType();
						String nodename=current.getNodeName();
						
						if (type==Node.ELEMENT_NODE && nodename!=null) {
							boolean matchedtag=false;
							int matchedindex=0;
							for (int j=0; j<numtags && !matchedtag; j++) {
								if (nodename.equals(expectedtags[j])) {
									matchedtag=true;
									matchedindex=j;
								}
							}
							
							if (matchedtag) {
								String value=RetrieveNodeValue(current);
								
								SetValueMethod(nodename, matchedindex, value);
								
							} else if (acceptsAttachments && nodename.equals("NumAttachments")) {
								String value=RetrieveNodeValue(current);

								int numAttachments=NumericUtils.intValue(value);
								
								NumAttachmentsMethod(numAttachments);
							} else if (acceptsAttachments && nodename.equals("AttachmentList")) {
								NodeList attachments=current.getChildNodes();
								if (attachments!=null) {
									int attachcount=attachments.getLength();
									for (int j=0; j<attachcount; j++) {
										Node attachnode=attachments.item(j);										
										if (attachnode!=null && attachnode.getNodeType()==Node.ELEMENT_NODE && attachnode.getNodeName().equals("Attachment")) {
											
											int AttachmentID=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentID"));
											String CustomerAttachmentKey=RetrieveSubnodeValue(attachnode, "CustomerAttachmentKey");
											int AttachmentNumber=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentNumber"));
											String AttachmentCreated=RetrieveSubnodeValue(attachnode, "AttachmentCreated");
											String AttachmentType=RetrieveSubnodeValue(attachnode, "AttachmentType");
											String AttachmentData=RetrieveSubnodeValue(attachnode, "AttachmentData");
											String AttachmentMIME=RetrieveSubnodeValue(attachnode, "AttachmentMIME");
											int AttachmentWidth=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentWidth"));
											int AttachmentHeight=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentHeight"));
											int AttachmentSize=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentSize"));
											int AttachmentDuration=NumericUtils.intValue(RetrieveSubnodeValue(attachnode, "AttachmentDuration"));
											String AttachmentStatus=RetrieveSubnodeValue(attachnode, "AttachmentStatus");
											String AttachmentRating=RetrieveSubnodeValue(attachnode, "AttachmentRating");
											String AttachmentCaption=RetrieveSubnodeValue(attachnode, "AttachmentCaption");
											
											AddAttachmentMethod(AttachmentID, CustomerAttachmentKey, AttachmentNumber, AttachmentCreated, AttachmentType, AttachmentData,
											  				    AttachmentMIME, AttachmentWidth, AttachmentHeight, AttachmentSize, AttachmentDuration,
															    AttachmentStatus, AttachmentRating, AttachmentCaption); 
										}
									}
								}											
								
							} else {
								if (WARN_ENABLED) logger.warn("ComHelper - "+rootname+" unexpected tag read "+nodename);
							}
						}
					}
				}
			} 
		}		
		
		
		return processed;				
	}


}

