<template>
  <b-container>
    <h1>Welcome to Simple Note!</h1>
    <b-row v-if="user === null">
      <b-col>
        <b-form @submit="setUser">
          <b-form-group
            label="Who is the author?"
          >
            <b-form-row>
              <b-col cols="10">
                <b-form-input v-model="author"></b-form-input>
              </b-col>
              <b-col cols="2">
                <b-button block variant="success" type="submit">Submit</b-button>
              </b-col>
            </b-form-row>
          </b-form-group>
        </b-form>
      </b-col>
    </b-row>
    <b-container v-if="user !== null">
      <b-row class="mb-3">
        <b-col cols="2">
          <b-button block variant="success" v-b-modal.modal-create-note>Create Note</b-button>
        </b-col>
        <b-modal id="modal-create-note" centered title="Create note" hide-footer>
          <b-container fluid>
            <b-row>
              <b-col>
                <b-form @submit="createNote">
                  <b-form-group
                    label="Title"
                  >
                    <b-form-input v-model="form.title" required></b-form-input>
                  </b-form-group>
                  <b-form-group
                    label="Description"
                    :description="description"
                    :invalid-feedback="invalidFeedback"
                    :state="state"
                  >
                    <b-form-textarea rows="5" v-model="form.description" :state="state" required></b-form-textarea>
                  </b-form-group>
                  <b-button-group>
                    <b-button variant="danger" @click="reset">Reset</b-button>
                    <b-button type="submit" variant="success">Create</b-button>
                  </b-button-group>
                </b-form>
              </b-col>
            </b-row>
          </b-container>
        </b-modal>
      </b-row>
      <b-row>
        <b-col cols="6" v-for="note in notes" :key="note.id">
          <Note :note="note" @deleted="removeNote"/>
        </b-col>
      </b-row>
    </b-container>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Note from '../components/Note'

  export default {
    name: "Landing",
    components: {Note},
    computed: {
      description() {
        return `Character length ${this.form.description.length}/250`
      },
      state() {
        return this.form.description !== "" && this.form.description.length <= 250;
      },
      invalidFeedback() {
        if (this.form.description.length > 250) {
          return "Description is too long"
        }
        return "Please fill the description"
      }
    },
    data() {
      return {
        user: localStorage.getItem("user"),
        author: "",
        form: {
          title: "",
          description: ""
        },
        notes: []
      };
    },
    methods: {
      setUser(e) {
        e.preventDefault();
        localStorage.setItem("user", this.author);
        this.user = this.author;
        this.fetchNotes();
      },
      fetchNotes() {
        axios.get(`/notes?author=${this.user}`)
          .then((response) => {
            const notes = response.data.data;
            notes.forEach((note) => {
              this.notes.push(note);
            })
          })
          .catch((error) => {
            console.log(error);
          })
      },
      createNote(e) {
        e.preventDefault();
        const request = {
          title: this.form.title,
          description: this.form.description,
          author: this.user
        }
        axios.post("/notes", request)
          .then((response) => {
            const note = response.data.data;
            this.notes.push(note);
            this.$bvModal.hide("modal-create-note")
            this.reset()
          })
          .catch((error) => {
            console.log(error);
          })
      },
      removeNote(id) {
        this.notes = this.notes.filter(note => note.id !== id)
      },
      reset() {
        this.form = {
          title: "",
          description: ""
        };
      }
    },
    created() {
      this.fetchNotes();
    }
  }
</script>

<style scoped>

</style>