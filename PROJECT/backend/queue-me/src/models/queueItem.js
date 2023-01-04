function QueueItem (user_id, course_id, location, comment){
    this.user_id = user_id;
    this.course_id = course_id;
    this.location = location;
    this.comment = comment;
}

module.exports = {QueueItem}