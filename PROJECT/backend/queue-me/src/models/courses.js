
function Courses (){
    this.nrcourses = 0;
    this.courselist = [];
}

Courses.prototype.addCourse = function (id , title, isQueueOpen){
    const courseToAdd = this.createCourse (id, title, isQueueOpen);
    this.courselist = [...this.courselist, courseToAdd];
    this.nrcourses++;
}

Courses.prototype.createCourse = function (c_id, ctitle, cisQueueOpen){
    return {id : c_id, title : ctitle, isQueueOpen: cisQueueOpen};
}

module.exports = {Courses};