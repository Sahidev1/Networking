
function QueueItems (course_id){
    this.course_id = course_id;
    this.courseQitems = [];
    this.nrItems = 0;
}

QueueItems.prototype.createQitem = function (queue_item_id, user_id, location, comment, username){
    return {id : queue_item_id, "course_id":this.course_id ,"user_id": user_id, "location": location, "comment": comment, "username": username};
}

QueueItems.prototype.addItem = function (q_item_id, user_id, location, comment, username){
    const newItem = this.createQitem (q_item_id, user_id, location, comment,username);
    this.courseQitems = [...this.courseQitems, newItem];
    this.nrItems++;
}

module.exports = {QueueItems};