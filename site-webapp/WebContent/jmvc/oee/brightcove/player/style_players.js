/**
 * 
 * @param playerObject
 */
function globalStyle(playerObject) {

}

function getProperty(playerObject, propertyName) {
	var property = hostAndContextName
			+ Oee.Brightcove.Player.assets[playerObject.videoPlayerType][propertyName];
	return property;
}

function getObjectProperty(playerObject, object, propertyName) {
	var property = hostAndContextName
			+ Oee.Brightcove.Player.assets[playerObject.videoPlayerType][object][propertyName];
	return property;
}

function getPropertyWithoutContext(playerObject, object, propertyName) {
	var property = Oee.Brightcove.Player.assets[playerObject.videoPlayerType][object][propertyName];
	return property;
}

/**
 * 
 * @param playerObject
 */
function defaultPlayer(playerObject) {
	chrome(playerObject);
}

/**
 * 
 * @param playerObject
 */
function beehivePlayer(playerObject) {
	chrome(playerObject);
}

function chrome(playerObject){
	setStyleFile(playerObject);
	setDefaultValues(playerObject);
}

function chromeless(playerObject){
	/* Closed Caption */
	setToggledIcons(playerObject, "captionButton", "cc");

	/* Add Comment */
	setToggledIcons(playerObject, "addCommentButton", "addcomment");

	setDefaultValues(playerObject);
}

function highSizePlayer(playerObject){
	chromeless(playerObject);
}
/**
 * 
 * @param playerObject
 */
function setDefaultValues(playerObject) {

	/* Transcript */
	setToggledIcons(playerObject, "transcriptButton", "transcript");

	/* Email */
	setToggledIcons(playerObject, "emailButton", "email");

	/* FullScreen */
	//setToggledIcons(playerObject, "fullscreenButton", "fullscreen");

	/*HBox Background*/
	//setBackgroundImage(playerObject,"defaultView" ,"background")
}


/***************************METHODS*****************************************/

function setStyleFile(playerObject){
//	var color = playerObject.playerColor;
	steal.dev.log("setStyleFile(playerObject): ");
	var mediaControls = playerObject.experience.getElementByID("mediaControls");
	var cssText = playerObject.translate(playerObject.styles.text());

	mediaControls.setStyles(cssText);
	//steal.dev.log(mediaControls.getStyleFile());
	//steal.dev.log(mediaControls);
}

function setToggledIcons(playerObject, id, propertyName) {
	icon = playerObject.experience.getElementByID(id);
	icon.setIconName(getObjectProperty(playerObject, propertyName, "off"));
	icon.setToggledIconName(getObjectProperty(playerObject, propertyName, "on"));
	icon.setSize(getPropertyWithoutContext(playerObject, propertyName, "width"), getPropertyWithoutContext(playerObject, propertyName, "height"));
}

function setToggledIconsWithText(playerObject, id, propertyName) {
	icon = playerObject.experience.getElementByID(id);
	icon.setLabel(getPropertyWithoutContext(playerObject, propertyName, "label"));
	icon.setSize(getPropertyWithoutContext(playerObject, propertyName, "width"), getPropertyWithoutContext(playerObject, propertyName, "height"));
}

function setBackgroundImage(playerObject, id, propertyName){
	obj = playerObject.experience.getElementByID(id);
	obj.setBackgroundImage(getObjectProperty(playerObject, propertyName, "img"));
	//obj.setSize(getPropertyWithoutContext(playerObject, propertyName, "width"), getPropertyWithoutContext(playerObject, propertyName, "height"));
}