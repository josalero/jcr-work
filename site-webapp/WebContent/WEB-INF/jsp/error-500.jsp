<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.Writer"%>

  <!-- EXCEPTION:
   
        <%

			Object codeObj = session.getAttribute("ABOUT_SITE_ERROR_500_CODE");
			String code = (codeObj != null) ? codeObj.toString() : "Unavailable";
    	
			Object messageObj = session.getAttribute("ABOUT_SITE_ERROR_500_MESSAGE");
			String message = (messageObj != null) ? messageObj.toString() : "Unavailable";

			Object typeObj = (String)session.getAttribute("ABOUT_SITE_ERROR_500_TYPE");
			String type = (typeObj != null) ? typeObj.toString() : "Unavailable";

			Throwable throwable = (Throwable)session.getAttribute("ABOUT_SITE_ERROR_500_EXCEPTION");

			String uri = (String)session.getAttribute("ABOUT_SITE_ERROR_500_URI");
			uri = (uri != null) ? uri : "Unavailable";

			String stackTrace = "Unavailable";
			
			if (throwable != null) {
				
		    	final Writer result = new StringWriter();
		        final PrintWriter printWriter = new PrintWriter(result);
		                
		        throwable.printStackTrace(printWriter);
		        stackTrace = result.toString();    
			}
	        
         %>

        Trace: <%=stackTrace%>
        Code: <%=code%>
        Message: <%=message%>
        Type: <%=type%>
        URI: <%=uri%>
        
        <%
        	session.removeAttribute("ABOUT_SITE_ERROR_500_CODE");
			session.removeAttribute("ABOUT_SITE_ERROR_500_MESSAGE");
			session.removeAttribute("ABOUT_SITE_ERROR_500_TYPE");
			session.removeAttribute("ABOUT_SITE_ERROR_500_EXCEPTION");
			session.removeAttribute("ABOUT_SITE_ERROR_500_URI");
        %>

   --!>


  <div class="error-main-container">
		<div class="error-left-content">
			<div class="error-header">
				  WE'RE SORRY.
			</div>
			<div class="error-summary">
				  The server encountered a temporary error and could not complete your request.
			</div>
		</div>
		<div class="error-right-content">
			<div class="error-subheader">
				   PLEASE TRY SEARCHING
			</div>
			
			<div id="searchHeaderContainer-error">
				<form method="POST" action="https://www.bankofamerica.com/search/index.jsp" name="index" id="index" target="_blank">
					<input type="hidden" id="index_searchSourceDir" value="aboutbac" name="searchSourceDir"/>
					<div class="wwgrp" id="wwgrp_index_questionbox">
						<div class="wwlbl" id="wwlbl_index_questionbox">
							<label class="label" for="index_questionbox">SEARCH:</label>
						</div> <br>
						<div class="wwctrl" id="wwctrl_index_questionbox">
							<input type="text" id="index_questionbox" tabindex="1" value="SEARCH" name="questionbox">
						</div> 
					</div>

					<div align="right" id="wwctrl_index_0"><input type="submit" tabindex="2" value="GO" id="index_0" title="Submit search and see results; results will show in a new window.">
					</div> 
				</form>
			</div>
			<div class="error-subheader">
				   OR VISIT
			</div>
			
			<div class="right-navigation-section-error">

				<ul class="right-navigation-content-error">
					<li class="right-navigation-item">
						<a href="/en-us/global-impact/strengthening-economies.html">
							Strengthening Economies
						</a>
					</li>
					<li class="right-navigation-item">
						<a href="/en-us/global-impact/leadership-and-service.html">
							Leadership &amp; Service
						</a>
					</li>
					<li class="right-navigation-item">
						<a href="/en-us/global-impact/environmental-sustainability.html">
							Environmental Sustainability
						</a>
					</li>
					<li class="right-navigation-item">
						<a href="/en-us/global-impact/arts-and-culture.html">
							Arts &amp; Culture	
						</a>
					</li>
					<li class="right-navigation-item last-right-navigation-item">
						<a href="/en-us/global-impact/responsible-business-practices.html">
							Responsible Practice	
						</a>
					</li>
				</ul>
			</div>
			
		</div>
		<div class="clear"></div>
  </div>
  
  
  
