var request = require('request-promised');
jasmine.getEnv().defaultTimeoutInterval = 500;

describe('Students', function() {
    it('should get students', function(done) {
        var student = {
            number: '123',
            first_name: 'James',
            last_name: 'Merson',
            gender: 'male'
        };

        request.post('http://localhost:8080/students', {body: student, json: true}).then(function(student){
            console.log(student)
            expect(student.id).toBe(null);
            done();
        });

    });
})

