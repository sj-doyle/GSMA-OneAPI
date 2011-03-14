
import urllib
from foundation.FormParameter import FormParameter

class FormParameters:

	def __init__(self):
		self.parameterSet=list()
		
	def put(self, key, value):
		fp=FormParameter(key, value)
		self.parameterSet.append(fp)
	
	def getParameterSet(self):
		return self.parameterSet
	
	def encodeParameters(self):
		from cStringIO import StringIO
  		con_str = StringIO()
  		first=True
  		for fp in self.parameterSet:
  			if fp.key is not None and fp.value is not None:
  				if not first:
  					con_str.write('&');
  				else:
  					first=False
  				con_str.write(urllib.urlencode({fp.key : fp.value}))
  		contents=con_str.getvalue()
  		con_str.close()
  		return contents
  				
