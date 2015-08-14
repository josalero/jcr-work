<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

	<c:forEach var="panel"
							items="${pageUIData.panelList}">
							
						
	</c:forEach>
	
	 <!-- Panel 4: "Leaders On The Ground" -->
    <div class="wrapper">
      <div class="bgFakeNext color-red"></div>
      <div id="leaders-on-the-ground" class="vPanel panel color-sandStone">
        <div class="backgroundOverlay"></div>
        <div class="contents">
          <div class="leadersContainer">
            <div class="paddingForHeader">
              <div class="leadersContainerPadding"><h2 class="text-red">Leaders on the ground</h2></div>
              <img src="images/fpo-leaders-map.png" style="width: 800px; height: 340px;" alt="Map of Leaders on the Ground.">
              <div class="leadersContainerPadding text-darkSand"><p>Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur.</p></div>
              <div class="leadersContainerPadding bottom">
                <div id="leadersInputForm" class="color-sandStone">
                  <div class="part1">
                    <strong>Lorem ipsum dolor sit amet, consectetur adipsing elit. Duis nec mi mauris:</strong><button class="button-red">start</button>
                  </div>
                  <div class="part2">
                    <div class="fields">
                      <p>Lorem ipsum dolor sit amet, consectetur adipsing elit. Duis nec mi mauris. Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo.</p>
                      <h3 class="text-red">Step 1: Provide Your Employee Information</h3>

                      <div class="leadersForm-row"><label for="firstName"><span class="text-red">*</span> First Name</label><input id="firstName" name="firstName" type="text"></div>
                      <div class="leadersForm-row"><label for="lastName"><span class="text-red">*</span> Last Name</label><input id="lastName" name="lastName" type="text"></div>
                      <div class="leadersForm-row"><label for="standardId"><span class="text-red">*</span> Standard ID</label><input id="standardId" name="standardId" type="text"></div>
                      <div class="leadersForm-row noLabel">(<a href="#">Standard ID Look-up Tool</a>)</div>
                      <div class="leadersForm-row"><label for="standardId"><span class="text-red">*</span> Company Email Address</label><input id="standardId" name="standardId" type="text"></div>
                      <div class="leadersForm-row noLabel">(If you do not have an email, please enter your mail code)</div>
                    </div>

                    <div class="bottomButton">
                      Lorem ipsum dolor sit amet. 
                      <button class="button-red">next</button>
                    </div>
                  </div>
                  <div class="part3">
                    <div class="fields">
                      <p>Lorem ipsum dolor sit amet, consectetur adipsing elit. Duis nec mi mauris. Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo.</p>
                      <h3 class="text-red">Step 2: Tell us why you want to be a reporter</h3>
                      <div class="leadersForm-row"><label for="submissionTitle"><span class="text-red">*</span> Submission Title</label><input id="submissionTitle" name="submissionTitle" type="text"></div>
                      <div class="leadersForm-row"><label for="submission"><span class="text-red">*</span> Submission</label><textarea id="submission" name="submission" type="text"></textarea></div>
                    </div>
                    <div class="bottomButton">
                      Lorem ipsum dolor sit amet. 
                      <button class="button-red">submit</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>