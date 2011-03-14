
require 'uri'
require 'net/http'
require 'cgi'
require 'response/HTTPResponse'

class JSONRequest

	def initialized
	  
	end
				
	def addQueryParameter(url, parameter, value)
		if (parameter!=nil) && (value!=nil) then
			if url.index('?')!=nil then
				url << '&'
			else
				url << '?'
			end
      url << CGI::escape(parameter.to_s)
      url << '='
      url << CGI::escape(value.to_s)
		end
		return url	
	end
	
  def get(url, responsetype, username, password)
    uri=URI.parse(url)
    req = Net::HTTP::Get.new(uri.request_uri)
    if responsetype!=nil then
      req["Accept"]="application/json"
    end
    req.basic_auth username, password
    response=HTTPResponse.new()
    begin
      httpresponse = Net::HTTP.new(uri.host, uri.port).start { |http| http.request(req) }
      response.setCode(httpresponse.code)
      response.setContent(httpresponse.body)
      response.setContentType(httpresponse.header['Content-Type'])
      response.setLocation(httpresponse.header['Location'])
    rescue => e
      print 'exception handled'
    end
    return response
  end
  
  def post(url, postdata, responsetype, username, password)
    uri=URI.parse(url)
    req = Net::HTTP::Post.new(uri.request_uri)
    if responsetype!=nil then
      req["Accept"]="application/json"
    end
    req.basic_auth username, password
    req.body=postdata
    response=HTTPResponse.new()
    begin
      httpresponse = Net::HTTP.new(uri.host, uri.port).start { |http| http.request(req) }
      response.setCode(httpresponse.code)
      response.setContent(httpresponse.body)
      response.setContentType(httpresponse.header['Content-Type'])
      response.setLocation(httpresponse.header['Location'])
    rescue => e
      print 'exception handled'
    end
    return response    
  end
 
  def postMultipart(url, postdata, responsetype, username, password, boundary)
    uri=URI.parse(url)
    req = Net::HTTP::Post.new(uri.request_uri)
    if responsetype!=nil then
      req["Accept"]="application/json"
    end
    
    req["Content-Type"]='multipart/mixed; boundary="'+boundary+'"';
      
    req.basic_auth username, password
    req.body=postdata
    response=HTTPResponse.new()
    begin
      httpresponse = Net::HTTP.new(uri.host, uri.port).start { |http| http.request(req) }
      response.setCode(httpresponse.code)
      response.setContent(httpresponse.body)
      response.setContentType(httpresponse.header['Content-Type'])
      response.setLocation(httpresponse.header['Location'])
    rescue => e
      print 'exception handled'
    end
    return response    
  end
 
  def delete(url, username, password)
    uri=URI.parse(url)
    req = Net::HTTP::Delete.new(uri.request_uri)
    req.basic_auth username, password
    response=HTTPResponse.new()
    begin
      httpresponse = Net::HTTP.new(uri.host, uri.port).start { |http| http.request(req) }
      response.setCode(httpresponse.code)
      response.setContent(httpresponse.body)
      response.setContentType(httpresponse.header['Content-Type'])
      response.setLocation(httpresponse.header['Location'])
    rescue => e
      print 'exception handled'
    end
    return response
  end

end
		