
export default class Observer {
    constructor(){
        this.observers = [];
    }

    addObserver (callback){
        this.observers = [...this.observers, callback];
    }

    removeObserver (callback){
        this.observers = this.observers.filter(el => !(Object.is(el,callback)));
    }

    notifyObserver (){
        const call = (cb) => {
            try {
                cb();
            } catch (err){
                console.log("Error:" + err);
            }
        }

        this.observers.forEach (callback => call(callback));
    }
}   
