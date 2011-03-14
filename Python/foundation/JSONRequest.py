
import urllib
import urllib2
import base64

from response.HTTPResponse import HTTPResponse
from urllib2 import HTTPError

class JSONRequest:

	def __init__(self):		
		self.initialised=True
				
	def addQueryParameter(self, url, parameter, value):
		if parameter is not None and value is not None:
			if '?' in url:
				url=url+'&'
			else:
				url=url+'?'
			url=url+urllib.urlencode({parameter : value})
		return url	
				
	def get(self, url, responsetype, username, password):
		opener = urllib2.build_opener(urllib2.HTTPHandler)
		request = urllib2.Request(url)
		if responsetype is not None:
			request.add_header('Accept', responsetype)
		request.get_method = lambda: 'GET'
		base64string = base64.encodestring('%s:%s' % (username,password))[:-1]
		request.add_header("Authorization", "Basic %s" % base64string)
		response=HTTPResponse(None)
		try:
			handle = opener.open(request)
			info=handle.info()
			content=handle.read()
			response.setCode(handle.getcode())
			response.setContent(content)
			print 'Response code = %d' % (handle.getcode())
			if info is not None:
				headers=info.dict
				if headers is not None:
					if 'location' in headers:
						response.setLocation(headers['location'])
					if 'content-type' in headers:
						response.setContentType(headers['content-type'])
		except HTTPError, e:
			print 'The server couldn\'t fulfill the request.'
			print 'Error code: ', e.code			
			response.setCode(e.code)
		return response

	def post(self, url, postdata, responsetype, username, password):
		opener = urllib2.build_opener(urllib2.HTTPHandler)
		request = urllib2.Request(url, postdata)
		if responsetype is not None:
			request.add_header('Accept', responsetype)
		request.get_method = lambda: 'POST'
		base64string = base64.encodestring('%s:%s' % (username,password))[:-1]
		request.add_header("Authorization", "Basic %s" % base64string)
		response=HTTPResponse(None)
		try:
			handle = opener.open(request)
			info=handle.info()
			content=handle.read()
			response.setCode(handle.getcode())
			response.setContent(content)
			print 'Response code = %d' % (handle.getcode())
			if info is not None:
				headers=info.dict
				if headers is not None:
					if 'location' in headers:
						response.setLocation(headers['location'])
					if 'content-type' in headers:
						response.setContentType(headers['content-type'])
		except HTTPError, e:
			print 'The server couldn\'t fulfill the request.'
			print 'Error code: ', e.code			
			response.setCode(e.code)
		return response

	def postMultipart(self, url, postdata, responsetype, username, password, boundary):
		opener = urllib2.build_opener(urllib2.HTTPHandler)
		request = urllib2.Request(url, postdata)
		if responsetype is not None:
			request.add_header('Accept', responsetype)
		request.get_method = lambda: 'POST'
		base64string = base64.encodestring('%s:%s' % (username,password))[:-1]
		request.add_header("Authorization", "Basic %s" % base64string)
		request.add_header("Content-Type", 'multipart/mixed; boundary="'+boundary+'"')
		response=HTTPResponse(None)
		try:
			handle = opener.open(request)
			info=handle.info()
			content=handle.read()
			response.setCode(handle.getcode())
			response.setContent(content)
			print 'Response code = %d' % (handle.getcode())
			if info is not None:
				headers=info.dict
				if headers is not None:
					if 'location' in headers:
						response.setLocation(headers['location'])
					if 'content-type' in headers:
						response.setContentType(headers['content-type'])
		except HTTPError, e:
			print 'The server couldn\'t fulfill the request.'
			print 'Error code: ', e.code			
			response.setCode(e.code)
		return response

	def delete(self, url, username, password):
		opener = urllib2.build_opener(urllib2.HTTPHandler)
		request = urllib2.Request(url)
		request.get_method = lambda: 'DELETE'
		base64string = base64.encodestring('%s:%s' % (username,password))[:-1]
		request.add_header("Authorization", "Basic %s" % base64string)
		response=HTTPResponse(None)
		try:
			handle = opener.open(request)
			info=handle.info()
			response.setCode(handle.getcode())
			print 'Response code = %d' % (handle.getcode())
			if info is not None:
				headers=info.dict
				if headers is not None:
					if 'location' in headers:
						response.setLocation(headers['location'])
					if 'content-type' in headers:
						response.setContentType(headers['content-type'])
		except HTTPError, e:
			print 'The server couldn\'t fulfill the request.'
			print 'Error code: ', e.code			
			response.setCode(e.code)
		return response

		