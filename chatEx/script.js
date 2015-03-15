var isAvalible = true;  //zer_ik
function changeState()
{
		if(isAvalible)
		{
			document.getElementById('state').className = "btn btn-primary btn-danger";
			document.getElementById('state').innerHTML = "Server is not available";
		}
		else
		{
			document.getElementById('state').className = "btn btn-primary btn-success";
			document.getElementById('state').innerHTML = "Server is available";
		}
		isAvalible = !isAvalible;
};
	
	function createMessage(msg,id,nickname)
	{
		kDiv = document.createElement('div');
		kDiv.id = id;
	    var aDiv = document.createElement('div');
	    aDiv.className = "chat-box-left";
	    aDiv.innerHTML = msg;
	    addButtons(aDiv,id);
	    kDiv.appendChild(aDiv);
	    
	    var bDiv = document.createElement('div');
	    bDiv.className = "chat-box-name-left";
	    var image = document.createElement('img');	
	    image.src = "http://vladsokolovsky.com/forums/uploads/profile/photo-36.jpg?_r=0";
	    image.alt = "bootstrap Chat box user image";
	    image.className = "img-circle";
	    bDiv.appendChild(image);
	    bDiv.innerHTML += new Date().toLocaleString();
	    bDiv.innerHTML += "- ";
	    bDiv.innerHTML += nickname;
	    kDiv.appendChild(bDiv);

		var cDiv = document.createElement('hr');
	    cDiv.className = "hr-clas";                        
        kDiv.appendChild(cDiv);
        document.getElementById('message').value = "";

        document.getElementById('myChat').appendChild(kDiv);
        document.getElementById('myChat').scrollTop = document.getElementById('myChat').scrollHeight;
		//-----------------------zer_ik----------------------------
		var messageNode = makeMessageNode( id, msg, new Date().toLocaleString() + "- " + nickname );
		addMessageNode( messageNode );
	}
	function sendClick() {
		createMessage(document.getElementById('message').value, document.getElementById('kol').value, document.getElementById('nick').value);
		document.getElementById('kol').value = parseInt(document.getElementById('kol').value) + 1;
		localStorage.setItem("currentID", document.getElementById('kol').value);
		$.ajax({
                        method: 'POST',
                        data: { message: document.getElementById('message').value }
        });
    };
    function deleteMessage(id)
    {
    	var b = document.getElementById('myChat');
    	(element = document.getElementById(id)).parentNode.removeChild(element);
		deleteMessageNode(id); // zer_ik
    };
    function editMessage(id)
    {
    	var elem = document.getElementById(id.slice(1));
    	(element = document.getElementById(id)).parentNode.removeChild(element);
    	var elema = elem.getElementsByClassName("chat-box-left")[0].innerHTML;
    	var message = elema.slice(0,elema.search('<'));
    	elem.getElementsByClassName("chat-box-left")[0].innerHTML = "";
    	var input = document.createElement('input');
    	input.type = "text";
    	input.className = "form-control";
    	input.value = message.trim();
    	input.id = id;
    	var inpGr = document.createElement('input-group');
    	inpGr.appendChild(input);
    	var but = document.createElement('span');
    	but.className = "input-group-btn";
    	var butSave = document.createElement('button');
    	butSave.className = "btn btn-info";
    	butSave.value = "Save";
    	butSave.id = id;
    	butSave.innerHTML = "Save";
    	butSave.addEventListener("click", function(){ save(id); });
    	but.appendChild(butSave);
    	inpGr.appendChild(but);
    	elem.getElementsByClassName("chat-box-left")[0].appendChild(inpGr);
    };
    function save(id)
    {
    	var elem = document.getElementById(id.slice(1));
    	var elemt = elem.getElementsByClassName("chat-box-left")[0];
    	var msg = elemt.getElementsByClassName("form-control")[0].value;
    	elem.getElementsByClassName("chat-box-left")[0].innerHTML = msg;
    	addButtons(elemt, id.slice(1));
		changeMessageNode( id.slice(1), msg ); // zer_ik
    };
    function addButtons(prev, id)
    {
	    var buttonRemove = document.createElement('button');
    	buttonRemove.className = "btn pull-right";
    	buttonRemove.id = id;
    	buttonRemove.style.marginTop= "-10px";
    	buttonRemove.addEventListener("click", function(){ deleteMessage(id); });
    	var but = document.createElement('span');
    	but.className = "glyphicon glyphicon-remove";
    	buttonRemove.appendChild(but);
    	prev.appendChild(buttonRemove);

    	var buttonEdit = document.createElement('button');
    	buttonEdit.className = "btn pull-right";
    	buttonEdit.id = 'd'+id;
    	buttonEdit.style.marginTop= "-10px";
    	buttonEdit.addEventListener("click", function(){ editMessage('d'+id); });
    	var but1 = document.createElement('span');
    	but1.className = "glyphicon glyphicon-pencil";
    	buttonEdit.appendChild(but1);
    	prev.appendChild(buttonEdit);
    };
	
    function login() // zer_ik
    {
		localStorage.setItem("nick" , document.getElementById('NickName').value);  
		localStorage.setItem("log", "true"); 					
		loginNick(document.getElementById('NickName').value);
    };
	
	function loginNick(nick) //zer_ik
	{
		document.getElementById('nick').value = nick;
    	element = document.getElementById('forLog');
    	element.innerHTML = "";
    	var lbl = document.createElement('h4');
    	lbl.innerHTML = document.getElementById('nick').value;
    	element.appendChild(lbl);
    	var buttonLog = document.createElement('button');
    	buttonLog.className = "btn pull-right";
    	buttonLog.innerHTML = "Log Out";
    	buttonLog.id = 'logout';
    	buttonLog.style.marginTop= "10px";
    	buttonLog.addEventListener("click", function(){ logout(); });
    	element.appendChild(buttonLog);
    	document.getElementById('forName').appendChild(lbl);
        var buttonEdit = document.createElement('button');
        buttonEdit.className = "btn";
        buttonEdit.innerHTML = "Edit Nick";
        buttonEdit.id = 'editNick';
        buttonEdit.addEventListener("click", function(){ editNick(); });
        document.getElementById('forName').appendChild(buttonEdit);
	}
	
    function editNick()
    {
        var elem = document.getElementById('forName');
        var nickname = elem.getElementsByTagName('h4')[0].innerHTML;
        elem.innerHTML = "";
        var input = document.createElement('input');
        input.type = "text";
        input.className = "form-control";
        input.value = nickname.trim();
        input.id = "nickEdit";
        var inpGr = document.createElement('input-group');
        inpGr.appendChild(input);
        var but = document.createElement('span');
        but.className = "input-group-btn";
        var butSave = document.createElement('button');
        butSave.className = "btn btn-info";
        butSave.value = "Save";
        butSave.id = 'saveNick';
        butSave.innerHTML = "Save";
        butSave.addEventListener("click", function(){ saveNick(); });
        but.appendChild(butSave);
        inpGr.appendChild(but);
        elem.appendChild(inpGr);
    };
    function saveNick()
    {
        document.getElementById('nick').value = document.getElementById('nickEdit').value; //zer_ik
		localStorage.setItem("nick" , document.getElementById('nick').value);
        element = document.getElementById('forName');
        element.innerHTML = "";
        var lbl = document.createElement('h4');
        lbl.innerHTML = document.getElementById('nick').value;
        element.appendChild(lbl);
        var buttonEdit = document.createElement('button');
        buttonEdit.className = "btn";
        buttonEdit.innerHTML = "Edit Nick";
        buttonEdit.id = 'editNick';
        buttonEdit.addEventListener("click", function(){ editNick(); });
        document.getElementById('forName').appendChild(buttonEdit);
    };
    function logout()
    {
		document.getElementById('nick').value = "";
		localStorage.setItem("log", "false"); // zer_ik
		localStorage.setItem("nick" , document.getElementById('nick').value); // zer_ik
		document.getElementById('forName').innerHTML = "";
		document.getElementById('forLog').innerHTML = "";
		elem = document.createElement('div');
		elem.className = "form-inline nav nav-pills pull-right";
		elem.style.marginTop = "10px";
		inp = document.createElement('input');
		inp.className = "input-medium search-query";
		inp.placeholder = "NickName";
		inp.id = "NickName";
		inp.type = "text";
		elem.appendChild(inp);
		elem.innerHTML += " ";
		inp2 = document.createElement('input');
		inp2.className = "input-medium search-query";
		inp2.placeholder = "Password";
		inp2.type = "password";
		elem.appendChild(inp2);
		elem.innerHTML += " ";
		elem.innerHTML += " <label class='checkbox'><input type='checkbox'> Remember me</label> ";
		var buttonLog = document.createElement('button');
    	buttonLog.className = "btn btn-primary";
    	buttonLog.innerHTML = "Login";
    	buttonLog.addEventListener("click", function(){ login(); });
    	elem.appendChild(buttonLog);
    	var buttonReg = document.createElement('button');
    	buttonReg.className = "btn btn-primary";
    	buttonReg.innerHTML = "Register";
    	elem.appendChild(buttonReg);
    	document.getElementById('forLog').appendChild(elem);
    };
	
	//----------------------------------------zer_ik------------------------------------------
	
	var messageList = [];
	var currentNickName = "NAME";
	var currentID = 1;
	var isLog = false;
	
	function makeMessageNode(id, text, info)
	{
		return {
			id : id,
			text : text,
			info : info
		};
	};
	
	function addMessageNode(node)
	{
		messageList.push(node);
		store();
	}
	
	function changeMessageNode(id, newText)
	{
		for(var i=0; i<messageList.length; i++)
			if(messageList[i].id==id)
			{
				messageList[i].text = newText;
				store();
				return;
			}
	}
	
	function deleteMessageNode(id)
	{
		for(var i=0; i<messageList.length; i++)
			if(messageList[i].id==id)
			{
				messageList.splice(i,1);
				store();
				return;
			}
	}
	
	function restoreMessage(i)
	{
		kDiv = document.createElement('div');
		kDiv.id = messageList[i].id;
	    var aDiv = document.createElement('div');
	    aDiv.className = "chat-box-left";
	    aDiv.innerHTML = messageList[i].text;
	    addButtons(aDiv,messageList[i].id);
	    kDiv.appendChild(aDiv);
	    
	    var bDiv = document.createElement('div');
	    bDiv.className = "chat-box-name-left";
	    var image = document.createElement('img');	
	    image.src = "http://vladsokolovsky.com/forums/uploads/profile/photo-36.jpg?_r=0";
	    image.alt = "bootstrap Chat box user image";
	    image.className = "img-circle";
	    bDiv.appendChild(image);
	    bDiv.innerHTML += messageList[i].info;	
	    kDiv.appendChild(bDiv);

		var cDiv = document.createElement('hr');
	    cDiv.className = "hr-clas";                        
        kDiv.appendChild(cDiv);
		
        document.getElementById('myChat').appendChild(kDiv);
        document.getElementById('myChat').scrollTop = document.getElementById('myChat').scrollHeight;
	}
	
	function restoreAllMessages()
	{
			for(var i=0; i<messageList.length; i++)
				restoreMessage(i);
	}
	
	function init()
	{
		var messages = localStorage.getItem("messages");
		if( messages )
			messageList = JSON.parse(messages);
		var nick = localStorage.getItem("nick");
		if( nick )
			document.getElementById('nick').value = nick;
		var id = localStorage.getItem("currentID");
		if( id )
			document.getElementById('kol').value = id;
		var log = localStorage.getItem("log");
		if( log && log=="true")
			loginNick(nick);
		else
			logout();
		restoreAllMessages();
	}
	
	function store()
	{
		localStorage.setItem("messages", JSON.stringify(messageList));
	}