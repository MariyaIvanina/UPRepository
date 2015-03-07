function changeState(st)
	{
		if(st == false)
		{
			document.getElementById('state').className = "btn btn-primary btn-danger";
			document.getElementById('state').innerHTML = "Server is not available";
		}
		else
		{
			document.getElementById('state').className = "btn btn-primary btn-success";
			document.getElementById('state').innerHTML = "Server is available";
		}
	};
	function createMessage(msg,id,nickname)
	{
		k = document.createElement('div');
		k.id = id;
	    var a = document.createElement('div');
	    a.className = "chat-box-left";
	    a.innerHTML = msg;
	    addButtons(a,id);
	    k.appendChild(a);
	    
	    var b = document.createElement('div');
	    b.className = "chat-box-name-left";
	    var image = document.createElement('img');
	    image.src = "http://vladsokolovsky.com/forums/uploads/profile/photo-36.jpg?_r=0";
	    image.alt = "bootstrap Chat box user image";
	    image.className = "img-circle";
	    b.appendChild(image);
	    b.innerHTML += new Date().toLocaleString();
	    b.innerHTML += "- ";
	    b.innerHTML += nickname;
	    k.appendChild(b);

		var c = document.createElement('hr');
	    c.className = "hr-clas";                        
        k.appendChild(c);
        document.getElementById('message').value = "";

        document.getElementById('myChat').appendChild(k);
        document.getElementById('myChat').scrollTop = document.getElementById('myChat').scrollHeight;
	}
	function sendClick() {
		createMessage(document.getElementById('message').value, document.getElementById('kol').value, document.getElementById('nick').value);
		document.getElementById('kol').value = document.getElementById('kol').value + 1;
		$.ajax({
                        method: 'POST',
                        data: { message: "adsffs" }
        });
    };
    function deleteMessage(id)
    {
    	var b = document.getElementById('myChat');
    	(element = document.getElementById(id)).parentNode.removeChild(element);
    };
    function editMessage(id)
    {
    	var b = document.getElementById(id.slice(1));
    	(element = document.getElementById(id)).parentNode.removeChild(element);
    	var a = b.getElementsByClassName("chat-box-left")[0].innerHTML;
    	var message = a.slice(0,a.search('<'));
    	b.getElementsByClassName("chat-box-left")[0].innerHTML = "";
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
    	b.getElementsByClassName("chat-box-left")[0].appendChild(inpGr);
    };
    function save(id)
    {
    	var b = document.getElementById(id.slice(1));
    	var t = b.getElementsByClassName("chat-box-left")[0];
    	var msg = t.getElementsByClassName("form-control")[0].value;
    	b.getElementsByClassName("chat-box-left")[0].innerHTML = msg;
    	addButtons(t, id.slice(1));
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
    function login()
    {
    	document.getElementById('nick').value = document.getElementById('NickName').value;
    	element = document.getElementById('forLog');
    	element.innerHTML = "";
    	var lbl = document.createElement('label');
    	lbl.innerHTML = document.getElementById('nick').value;
    	element.appendChild(lbl);
    	var buttonEdit = document.createElement('button');
    	buttonEdit.className = "btn pull-right";
    	buttonEdit.innerHTML = "Log Out";
    	buttonEdit.id = 'logout';
    	buttonEdit.style.marginTop= "10px";
    	buttonEdit.addEventListener("click", function(){ logout(); });
    	element.appendChild(buttonEdit);
    	document.getElementById('forName').appendChild(lbl);
    };
    function logout()
    {
		document.getElementById('nick').value = "";
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